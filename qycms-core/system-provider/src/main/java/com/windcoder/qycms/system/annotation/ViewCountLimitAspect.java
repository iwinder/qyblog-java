package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//@Aspect
//@Configuration
public class ViewCountLimitAspect {
    @Autowired
    private RedisUtil redisUtil;
    @Pointcut("@annotation(com.windcoder.qycms.system.annotation.ViewCountLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        Object[] object = joinPoint.getArgs();
        Object blogId = object[0];
        Object obj = null;
        try {

            obj = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}
