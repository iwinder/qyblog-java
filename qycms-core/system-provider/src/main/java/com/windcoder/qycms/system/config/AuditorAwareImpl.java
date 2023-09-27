package com.windcoder.qycms.system.config;

import com.auth0.jwt.JWT;
import com.windcoder.qycms.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl  implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        // TODO: 解决非登录情况下修改数据时未获取到用户信息报错问题
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        if(securityManager == null){
            Long minaId = getUserFromMinaJWT();
            return Optional.of(new User(minaId !=null ? minaId : 1L));
        }
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(null != principal && principal instanceof User){
            return ((Optional<User>) principal);
        }else {
            Long minaId = getUserFromMinaJWT();
//            return new Optional<User>(1L);
            return Optional.of(new User(minaId !=null ? minaId : 1L));
            // throw new UnauthenticatedException();
        }
    }


    private Long getUserFromMinaJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("X-WX-Skey");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return JWT.decode(token).getClaim("wx-mina-user-id").asLong();
    }
}
