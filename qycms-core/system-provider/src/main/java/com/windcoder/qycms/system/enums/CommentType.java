package com.windcoder.qycms.system.enums;

public enum  CommentType {
    GENERAL("G", "普通"),
    PINGBACK("P", "通知");
    private String code;

    private String desc;

    CommentType(){}
    CommentType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
