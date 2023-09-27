package com.windcoder.qycms.system.shiro.jwt;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response){
        String auth = getAuthzHeader(request);
        return auth != null && !auth.equals("");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            JwtToken token = new JwtToken(getAuthzHeader(request));
            getSubject(request, response).login(token);
        }
        return  true;
    }
}
