package com.windcoder.qycms.core.basis.comment.dto;


import java.util.Date;

public class CommentDto {

    private Long id;


    private String content;

    /**
     * 评论者
     */
    private String author;



    /**
     * 评论者网址
     */
    private String url;

    private Integer replyCount;

    private Date createdDate;

    private CommentParentDto parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public CommentParentDto getParent() {
        return parent;
    }

    public void setParent(CommentParentDto parent) {
        this.parent = parent;
    }
}
