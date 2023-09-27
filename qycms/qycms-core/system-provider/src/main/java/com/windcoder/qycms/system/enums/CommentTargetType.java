package com.windcoder.qycms.system.enums;

public enum CommentTargetType {
    ARTICLE("A", "文章"),
    PAGE("P", "页面");
    private String code;

    private String desc;

    CommentTargetType(){}
    CommentTargetType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
