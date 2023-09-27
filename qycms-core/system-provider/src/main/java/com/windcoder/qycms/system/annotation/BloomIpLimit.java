package com.windcoder.qycms.system.annotation;

import java.lang.annotation.*;

/**
 * Ip黑名单布隆过滤器
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BloomIpLimit {
    /**
     * 描述
     */
    String description() default "";
}
