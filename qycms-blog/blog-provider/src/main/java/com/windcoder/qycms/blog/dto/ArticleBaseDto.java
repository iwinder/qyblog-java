package com.windcoder.qycms.blog.dto;

import com.windcoder.qycms.core.system.entity.User;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Lob;

public class ArticleBaseDto {

    private Long id;
    /**
     * 标题
     */
    private String title;


    /**
     * 是否已发布
     */
    private Boolean isPublished;

    /**
     * 是否已删除
     */
    private Boolean isDeleted;

    /**
     * 链接
     */
    private String permaLink;

    /**
     * 作者
     */
    private User author;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        this.isPublished = published;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

}
