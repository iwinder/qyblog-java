package com.windcoder.qycms.system.annotation;

import java.lang.annotation.*;

/**
 * <p>绑定当前登录的用户</p>
 * <p>不同于@ModelAttribute</p>
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    /**
     * 当前用户在session中的名字 默认{@link "userToken"}
     *
     * @return
     */
    String value() default "userToken";
}
