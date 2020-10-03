package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.exception.NotFoundException;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import com.windcoder.qycms.system.filters.BloomIpCacheFilter;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Aspect
@Configuration
@Order(1)
public class BloomIpLimitAspect {
    @Autowired
    private RedisUtil redisUtil;
    //Service层切点  限流
    @Pointcut("@annotation(com.windcoder.qycms.system.annotation.BloomIpLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] object = joinPoint.getArgs();
        Object obj;
        String key = IpAddressUtil.getClientRealIp();
        StringBuilder newkey = new StringBuilder(redisUtil.IPBLACK_FREQUENT_ACCESS);
        newkey.append(key);
        StringBuilder notFountkey = new StringBuilder(redisUtil.IPBLACK_NOT_FOUNT);
        notFountkey.append(key);
        StringBuilder notUserNameFountkey = new StringBuilder(redisUtil.IPBLACK_USERNAME_NOT_FOUNT);
        notFountkey.append(key);

        if(!BloomIpCacheFilter.mightContain(key) &&
            redisUtil.getOpsValue(newkey.toString()).longValue()< redisUtil.IPBLACK_FREQUENT_LIMIT_NUM  &&
            redisUtil.getOpsValue(notFountkey.toString()).longValue()< redisUtil.IPBLACK_NOT_FOUNT_LIMIT_NUM &&
            redisUtil.getOpsValue(notUserNameFountkey.toString()).longValue()< redisUtil.IPBLACK_USERNAME_NOT_FOUNT_LIMIT_NUM){
            obj = joinPoint.proceed();
        }else{
            throw new LimitException("请稍候尝试");
        }

        return obj;
    }
}
