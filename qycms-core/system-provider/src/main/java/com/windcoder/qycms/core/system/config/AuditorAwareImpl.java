package com.windcoder.qycms.core.system.config;

import com.windcoder.qycms.core.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<User> {
    @Override
    public User getCurrentAuditor() {
        // TODO: 解决非登录情况下修改数据时未获取到用户信息报错问题
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        if(securityManager == null){
            return new User(1L);
        }
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(null != principal && principal instanceof User){
            return ((User) principal);
        }else {
            return new User(1L);
            // throw new UnauthenticatedException();
        }
    }

}
