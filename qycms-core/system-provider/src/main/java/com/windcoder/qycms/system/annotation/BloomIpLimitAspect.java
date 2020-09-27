package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.exception.NotFoundException;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import com.windcoder.qycms.system.filters.BloomIpCacheFilter;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Aspect
@Configuration
@Order(1)
public class BloomIpLimitAspect {
    //Service层切点  限流
    @Pointcut("@annotation(com.windcoder.qycms.system.annotation.BloomLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] object = joinPoint.getArgs();
        Object obj;
        String key = IpAddressUtil.getClientRealIp();
        if(BloomIpCacheFilter.mightContain(String.valueOf(key))){
            obj = joinPoint.proceed();
        }else{
            throw new LimitException("请稍候尝试");
        }

        return obj;
    }
}
