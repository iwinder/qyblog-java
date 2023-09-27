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


    private Integer  replyActCount;
    private Date createdDate;





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


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }




    public Integer getReplyActCount() {
        return replyActCount;
    }

    public void setReplyActCount(Integer replyActCount) {
        this.replyActCount = replyActCount;
    }
}
