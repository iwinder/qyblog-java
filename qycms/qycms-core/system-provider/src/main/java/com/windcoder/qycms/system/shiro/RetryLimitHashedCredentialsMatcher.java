package com.windcoder.qycms.system.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.RedisCache;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private Cache<String, Object> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//         r =(RedisManager)cacheManager.getCache()

    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info){
        String username = (String)token.getPrincipal();
        //retry count+1;
        Object d = passwordRetryCache.get(username);
        AtomicInteger retryCount = (AtomicInteger) passwordRetryCache.get(username);
        if (retryCount == null){
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }

        if (retryCount.incrementAndGet()>5){
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException("登录错误次数超过多");
        }

        boolean matches = super.doCredentialsMatch(token,info);
        if(matches){
            passwordRetryCache.remove(username);
        } else {
            passwordRetryCache.put(username, retryCount);
        }
        return  matches;
    }
}
