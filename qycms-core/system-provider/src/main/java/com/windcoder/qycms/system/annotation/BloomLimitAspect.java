package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.exception.NotFoundException;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Aspect
@Configuration
@Order(2)
public class BloomLimitAspect {
    @Autowired
    private RedisUtil redisUtil;

    //Service层切点  限流
    @Pointcut("@annotation(com.windcoder.qycms.system.annotation.BloomLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        Object[] object = joinPoint.getArgs();
        Object blogLink = object[0];
        Object obj;
        try {
            if(BloomCacheFilter.mightContain(String.valueOf(blogLink))){
                obj = joinPoint.proceed();
            }else{
                throw new NotFoundException("小同志，你访问的文章不存在");
            }
        } catch (Throwable e) {
            throw new NotFoundException("小同志，你访问的文章不存在");
        }
        return obj;
    }
}
