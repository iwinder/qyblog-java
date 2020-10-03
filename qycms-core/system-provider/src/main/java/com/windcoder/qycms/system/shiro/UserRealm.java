package com.windcoder.qycms.system.shiro;

import com.windcoder.qycms.entity.GlobalProperties;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.system.service.PermissionService;
import com.windcoder.qycms.system.service.UserService;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private GlobalProperties globalProperties;
    @Autowired
    private RedisUtil redisUtil;




    /**
     * 用于检测密码错误次数
     */
//    @PostConstruct
//    public void initCredentialsMatcher() {
//        setCredentialsMatcher(new RetryLimitHashedCredentialsMatcher(shiroRedisCacheManager));
//    }

    /**
     *  用于授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        if (user != null){
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRoles(permissionService.findPermissionRolesOfUser(user));
            authorizationInfo.addStringPermissions(permissionService.findPermissionPrivilegesByUser(user));
            return authorizationInfo;
        }
        return null;
    }

    /**
     *  定义如何获取用户信息的业务逻辑，给shiro做登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 将AuthenticationToken强转为AuthenticationToken对象
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        // 获取从表单传过来的用户名
        String username = upToken.getUsername();

        User user = userService.findByUsername(username);

        checkUser(user);


        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(globalProperties.getToken()+user.getCredentialsSalt()), getName());
    }

    private void checkUser(User user) {
        if (null == user ) {
            saveError(user.getUsername());
            throw new AuthenticationException("用户名或密码错误");
        }
        if (user.getUsername() == null  ) {
            throw new AuthenticationException("用户名或密码错误");
        }
        if(user.getDisable()){
            throw new AuthenticationException("用户已锁定，请联系管理员！");
        }
//        if(user.getEndDate() != null && user.getEndDate().before(new Date())){
//            throw new AuthenticationException("帐号已到期，禁止登录系统！");
//        }
    }

    public void saveError(String username) {
        String key = IpAddressUtil.getClientRealIp();
        StringBuilder newkey = new StringBuilder(redisUtil.IPBLACK_USERNAME_NOT_FOUNT);
        newkey.append(key);
        long num = redisUtil.increment(newkey.toString());
        if(num >= Long.valueOf(redisUtil.IPBLACK_USERNAME_NOT_FOUNT_LIMIT_NUM).longValue()) {
            redisUtil.saveBlack(key, AgentUserUtil.getUserAgent(),
                    IpBlackType.LOGIN.name(),username + "用户名不存在过多");
        }
    }

}
