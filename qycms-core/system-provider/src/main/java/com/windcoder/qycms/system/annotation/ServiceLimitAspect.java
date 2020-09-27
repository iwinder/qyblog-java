package com.windcoder.qycms.system.annotation;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
@Order(2)
public class ServiceLimitAspect {
    @Autowired
    private RedisUtil redisUtil;

    //根据IP分不同的令牌桶, 每天自动清理缓存
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key){
                    // 新的IP初始化 每秒只发出5个令牌
                    return RateLimiter.create(5);
                }
            });
    //Service层切点  限流
    @Pointcut("@annotation(com.windcoder.qycms.system.annotation.ServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
        ServiceLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;

        if(limitType.equals(ServiceLimit.LimitType.IP)){
            key = IpAddressUtil.getClientRealIp();
        }
        RateLimiter rateLimiter = caches.get(key);
        Boolean flag = rateLimiter.tryAcquire();
        if(flag){
            obj = joinPoint.proceed();
        }else{
            StringBuilder newkey = new StringBuilder(redisUtil.IPBLACK_FREQUENT_ACCESS);
            newkey.append(key);
            long num = redisUtil.increment(newkey.toString());
            if(num >= Long.valueOf(redisUtil.IPBLACK_FREQUENT_LIMIT_NUM).longValue()) {
                JSONObject info = new JSONObject();
                info.put("ip", key);
                info.put("agent", AgentUserUtil.getUserAgent());
                info.put("type", "FREQUENTACCESS");
                info.put("remarks", "访问的太频繁");
                redisUtil.setIpBlackTmpInfo(info);
            }
            throw new LimitException("小同志，你访问的太频繁了");

        }

        return obj;
    }
}
