package com.windcoder.qycms.blog.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private String content;

    private String author;

    /**
     * 评论者邮件
     */
    private String email;

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

    @ManyToOne
    @JoinColumn(name="top_parent_id")
//    @JsonSerialize(using = SimpleCommentJsonSerializer.class)
    private Comment topParent;

    /**
     * 回复数
     */
    @Formula("(select count(scm.id) from sns_comment scm where scm.top_parent_id = id)")
//    @JsonView(SimpleView.class)
    private Integer replyCount;
}
