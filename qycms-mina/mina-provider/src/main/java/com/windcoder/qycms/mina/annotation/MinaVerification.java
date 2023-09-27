package com.windcoder.qycms.mina.annotation;

import java.lang.annotation.*;

/**
 * 校验是否来自微信小程序
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MinaVerification {
}
