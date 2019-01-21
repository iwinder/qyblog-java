package com.windcoder.qycms.blog.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private String content;

    /**
     * 评论者邮件
     */
    private String mail;

    /**
     * 评论者网址
     */
    private String url;

    /**
     * 评论者ip地址
     */
    private String ip;

    /**
     * 评论状态
     */
    private String status;

    /**
     * 评论者客户端
     */
    private String agent;

    private Comment parent;
}
