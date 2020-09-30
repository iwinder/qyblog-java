package com.windcoder.qycms.system.enums;

public enum IpBlackType {
    FREQUENTACCESS("F", "频繁访问"),
    NOTFOUNT("N", "访问不存在页面过多"),
    SYSTEM("S", "拒绝");
    private String code;

    private String desc;

    IpBlackType(){}
    IpBlackType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
