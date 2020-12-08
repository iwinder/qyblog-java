package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.Constants;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
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
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String borderGroup = AgentUserUtil.getBorderGroup(request);
            if (!borderGroup.equalsIgnoreCase("Robot/Spider")) {
                String value = IpAddressUtil.getClientRealIp();
                if(value.equals("127.0.0.1")) {
                    return joinPoint.proceed();
                }
                String key = new StringBuilder(redisUtil.POST_VIEW_COUNT).append(blogId).toString();
                Long flag = redisUtil.addPostViewCount(key,value);
                if(flag.longValue()>0) {
                    obj = joinPoint.proceed();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}
