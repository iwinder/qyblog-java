package com.windcoder.qycms.system.enums;

public enum IpBlackType {
    FREQUENTACCESS("F", "频繁访问"),
    ACCESSVIOLATION("AV", "非法访问"),
    NOTFOUNT("N", "访问不存在页面过多"),
    LOGIN("L", "登录异常"),
    SYSTEM("S", "系统");
    private String code;

    private String desc;

    IpBlackType(){}
    IpBlackType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
