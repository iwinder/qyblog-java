package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.exception.NotFoundException;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.Constants;
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
@Order(3)
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
                String key = IpAddressUtil.getClientRealIp();
                key = Constants.REDIS_TEST_IP;
                StringBuilder newkey = new StringBuilder(redisUtil.IPBLACK_NOT_FOUNT);
                newkey.append(key);
                long num = redisUtil.increment(newkey.toString());
                if(num >= Long.valueOf(redisUtil.IPBLACK_NOT_FOUNT_LIMIT_NUM).longValue()) {
                    redisUtil.saveBlack(key,AgentUserUtil.getUserAgent(),IpBlackType.NOTFOUNT.name(), "访问不存在的文章过多");
                }
                throw new NotFoundException("小同志，你访问的文章不存在");
            }
        } catch (Throwable e) {
            throw new NotFoundException("小同志，你访问的文章不存在");
        }
        return obj;
    }
}
