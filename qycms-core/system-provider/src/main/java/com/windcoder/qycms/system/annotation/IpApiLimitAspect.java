package com.windcoder.qycms.system.annotation;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.Constants;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
@Order(2)
public class IpApiLimitAspect {
    @Autowired
    private RedisUtil redisUtil;

    //根据方法名+IP分不同的令牌桶, 每天自动清理缓存
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key){
                    // 新的IP初始化 每秒只发出5个令牌
                    return RateLimiter.create(3);
                }
            });
    //Service层切点  限流
    @Pointcut("@annotation(com.windcoder.qycms.system.annotation.IpApiLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        IpApiLimit limitAnnotation = method.getAnnotation(IpApiLimit.class);
        IpApiLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;
        String ip = null;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userAgent = AgentUserUtil.getUserAgent(request).toLowerCase();
        if (userAgent.contains("python")||userAgent.contains("zgrab")) {
            // 爬虫类-非法访问
            ip = IpAddressUtil.getClientRealIp();
            redisUtil.saveBlack(ip,AgentUserUtil.getUserAgent(),IpBlackType.ACCESSVIOLATION.name(), "非正常访问");
            throw new LimitException("小同志，你访问的太频繁了");
        }

        if(limitType.equals(IpApiLimit.LimitType.IP)){
            ip = IpAddressUtil.getClientRealIp();
            if(ip.equals("127.0.0.1")) {
                return joinPoint.proceed();
            }
            key = method.getName() + ":" + ip;
        }
        RateLimiter rateLimiter = caches.get(key);
        Boolean flag = rateLimiter.tryAcquire(limitAnnotation.timeout(),limitAnnotation.timeunit());
        if(flag){
            obj = joinPoint.proceed();
        }else{
            redisUtil.saveBlack(ip,AgentUserUtil.getUserAgent(),IpBlackType.FREQUENTACCESS.name(), "访问的太频繁");

            throw new LimitException("小同志，你访问的太频繁了");
        }

        return obj;
    }
}
