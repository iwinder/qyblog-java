package com.windcoder.qycms.generator.utils;

public class GeneratorPathUtils {
    // Java

    static final String BLOG_MODULE = "blog";
    static final String BLOG_SERVICE_URL = "qycms-blog/blog-provider";
    static final String BLOG_CONSOLE_URL = "qycms-blog/blog-console";

    static final String SYSTEM_MODULE = "system";
    static final String SYSTEM_SERVICE_URL = "qycms-core/system-provider";
    static final String SYSTEM_CONSOLE_URL = "qycms-core/system-console";


    public static  String MODULE =  SYSTEM_MODULE;
    public static  String PROJECT_URL =  SYSTEM_SERVICE_URL;
    public static  String PROJECT_CONSOLE_URL = SYSTEM_CONSOLE_URL;

    // VUE
    static final String TO_VUE_BASE_PATH = "/home/wind/work/Web/qyblog-web/";
//    public static  String VUE_MODULE = "category";
//    static final String TO_VUE_CONSOLE_PROJECT = TO_VUE_BASE_PATH +"qy-console/pages/";

    public static  String VUE_MODULE = "user";
    public static final String TO_VUE_CONSOLE_PROJECT = TO_VUE_BASE_PATH +"qy-console/pages/";


}
