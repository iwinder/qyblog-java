package com.windcoder.qycms.system.annotation;

import java.lang.annotation.*;

/**
 * 布隆过滤器
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface BloomLimit {
    /**
     * 描述
     */
    String description() default "";
}
