package com.windcoder.qycms.system.annotation;

import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.exception.LimitException;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.system.utils.IpWhilteUtil;
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
            String ip = IpAddressUtil.getClientRealIp();
            // ip白名单
            if(IpWhilteUtil.isPermited(ip)) {
                return joinPoint.proceed();
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String borderGroup = AgentUserUtil.getBorderGroup(request);
            String userAgent = AgentUserUtil.getUserAgent(request).toLowerCase();
            if (blogId==null||userAgent.contains("python")||userAgent.contains("zgrab")) {
                // 爬虫类-非法访问

                redisUtil.saveBlack(ip,AgentUserUtil.getUserAgent(), IpBlackType.ACCESSVIOLATION.name(), "非正常访问");
                throw new LimitException("小同志，你访问的太频繁了");
            }
            if (!borderGroup.equalsIgnoreCase("Robot/Spider")) {
                String key = new StringBuilder(redisUtil.POST_VIEW_COUNT).append(blogId).toString();
                Long flag = redisUtil.addPostViewCount(key,ip);
                if(flag.longValue()>0) {
                    obj = joinPoint.proceed();
                }
            }
        } catch (Throwable e) {
            throw new BusinessException("浏览记录增加异常");
        }
        return obj;
    }
}
