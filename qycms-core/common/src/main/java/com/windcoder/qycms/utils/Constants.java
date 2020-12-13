package com.windcoder.qycms.utils;

import java.util.concurrent.TimeUnit;

public class Constants {

    // Redis
    public static final String PREFIX_FILE_LIBLE_CONFIG = "qy:fileLibConfig";
    public static final String PREFIX_FILE_LIBLE_CONFIG_KEY = "type";
    // 七牛云配置
    public static final String QINIU_ACCESS_KEY = "qiniu_access_key";
    public static final String QINIU_SECRET_KEY = "qiniu_secret_key";
    public static final String QINIU_BUCKET = "qiniu_bucket";
    public static final String QINIU_IMAGE_DOMAIN = "qiniu_image_domain";
    public static final String REDIS_TEST_IP = "106.121.187.122";
    // 默认过期时间 500
    public static final long REDIS_DEFAULT_TIMEOUT =  500;
    // 默认过期时间单位  毫秒
    public static final TimeUnit REDIS_DEFAULT_TIMEUNIT =  TimeUnit.MILLISECONDS;
}
