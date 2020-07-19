package com.windcoder.qycms.utils;

import com.windcoder.qycms.exception.ValidatorException ;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class ValidatorUtil {
    /**
     * 空校验（null or ""）
     */
    public static void require(Object  str, String fieldName) {
        if (StringUtils.isEmpty(str)) {
            throw new ValidatorException(fieldName + "不能为空");
        }
    }

    /**
     * 长度校验
     */
    public static void length(String str, String fieldName, int min, int max) {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        int length = 0;
        if (!StringUtils.isEmpty(str)) {
            length = str.length();
        }
        if (length < min || length > max) {
            throw new ValidatorException(fieldName + "长度" + min + "~" + max + "位");
        }
    }

    public static void isEmail(Object  str) {
        String pattern = "^([a-zA-Z\\d])(\\w|\\-)+@[a-zA-Z\\d]+\\.[a-zA-Z]{2,6}$";
        boolean isMatch = Pattern.matches(pattern, str.toString());
        if (!isMatch) {
            throw new ValidatorException("邮箱格式错误");
        }
    }

}
