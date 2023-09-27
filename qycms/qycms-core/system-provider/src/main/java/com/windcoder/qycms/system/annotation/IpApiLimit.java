package com.windcoder.qycms.system.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * ip访问单个接口的限流注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IpApiLimit {
    /**
     * 描述
     */
    String description() default "";
    /**
     * key
     */
    String key() default "";


    /**
     * 获取令牌等待超时时间 默认:500
     * @return
     */
    long timeout() default 500;

    /**
     *  超时时间单位 默认:毫秒
     * @return
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 类型
     */
    LimitType limitType() default LimitType.CUSTOMER;

    enum LimitType {
        /**
         * 自定义key
         */
        CUSTOMER,
        /**
         * 根据请求者IP
         */
        IP
    }
}
