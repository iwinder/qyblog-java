package com.windcoder.qycms.system.enums;

public enum CommenttTargetType {
    ARTICLE("A", "文章"),
    PAGE("P", "页面");
    private String code;

    private String desc;

    CommenttTargetType(){}
    CommenttTargetType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
