package com.windcoder.qycms.core.system.shiro;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.core.system.service.PermissionService;
import com.windcoder.qycms.core.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm{
    //

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;





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
//            authorizationInfo.addRoles(permissionService.findPermissionRolesOfUser(userToken.getUser()));
            authorizationInfo.addStringPermissions(permissionService.findPermissionPrivilegesByUser());
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

        if (user == null){
            throw new UnknownAccountException("无此用户名！");
        }

        if (user.getIsDisable()){
            throw new LockedAccountException();
        }


        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
    }
}
