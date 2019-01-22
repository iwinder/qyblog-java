package com.windcoder.qycms.core.basis.comment.dto;

public class CommentParentDto {
    private Long id;
    /**
     * 评论者
     */
    private String author;



    /**
     * 评论者网址
     */
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
