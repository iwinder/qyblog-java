package com.windcoder.qycms.system.enums;

public enum  CommentStatus {
    APPLIED("A", "文章"),
    ENROLLED("E", "页面"),
    REFUSED("E", "页面"),
    DELETED("D", "页面");
    private String code;

    private String desc;

    CommentStatus(){}
    CommentStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
