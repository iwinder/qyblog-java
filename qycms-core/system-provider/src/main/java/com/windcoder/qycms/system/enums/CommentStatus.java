package com.windcoder.qycms.system.enums;

public enum  CommentStatus {
    APPLIED("A", "待审核"),
    ENROLLED("E", "通过"),
    REFUSED("E", "拒绝"),
    DELETED("D", "删除");
    private String code;

    private String desc;

    CommentStatus(){}
    CommentStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
