package com.windcoder.qycms.blog.enums;

public enum BlogArticleStatus {
    PUBLIC("PU", "公共"),
    PRIVATE("PR", "私密"),
    ENCRYPTION("E", "加密");
    private String code;

    private String desc;

    BlogArticleStatus(){}
    BlogArticleStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
