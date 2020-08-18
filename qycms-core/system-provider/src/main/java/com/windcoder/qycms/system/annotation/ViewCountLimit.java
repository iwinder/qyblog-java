package com.windcoder.qycms.system.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewContLimit {
    /**
     * 描述
     */
    String description() default "";
}
