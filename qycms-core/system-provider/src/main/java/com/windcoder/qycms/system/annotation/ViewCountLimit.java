package com.windcoder.qycms.system.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewCountLimit {
    /**
     * 描述
     */
    String description() default "";
}
