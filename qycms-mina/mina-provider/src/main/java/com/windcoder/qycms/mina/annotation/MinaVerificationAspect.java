package com.windcoder.qycms.mina.annotation;

import com.windcoder.qycms.entity.GlobalProperties;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.Constants;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
@Order(4)
public class MinaVerificationAspect {
    @Autowired
    @Lazy
    private GlobalProperties globalProperties;

    @Pointcut("@annotation(com.windcoder.qycms.mina.annotation.MinaVerification)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        long now = System.currentTimeMillis();
        Object[] object = joinPoint.getArgs();
        Object blogId = object[0];
        Object obj = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String referer = request.getHeader("Referer").toLowerCase();
            String token = request.getHeader(Constants.MINA_HEADER_TOKEN_KEY);
            String startTimeStr = request.getHeader(Constants.MINA_HEADER_TIME_KEY);
            if (referer.contains("servicewechat.com")&&referer.contains("wx72b9cfef32a63dde")&& StringUtils.isNotBlank(token) && StringUtils.isNotBlank(startTimeStr)) {
                // 属于微信小程序
                String miniSlat = globalProperties.getMinaToken();
                long startTime = Long.valueOf(startTimeStr);
                String tmpMd5 = DigestUtils.md5DigestAsHex(startTimeStr.getBytes()) + miniSlat;
                String nowMd5 = DigestUtils.md5DigestAsHex(tmpMd5.getBytes());
                if (nowMd5.equals(token)) {
                   long exc = now - startTime;
                   if (exc>0 && (exc/1000)<=180) {
                       return joinPoint.proceed();
                   }
                }
            }
        } catch (Throwable e) {
            throw new BusinessException("获取相关请求异常");
        }
        throw new BusinessException("请求异常");
    }
}
