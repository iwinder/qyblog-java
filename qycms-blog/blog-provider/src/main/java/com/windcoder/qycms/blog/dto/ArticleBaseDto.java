package com.windcoder.qycms.blog.dto;

import com.windcoder.qycms.core.system.dto.UserDto;
import com.windcoder.qycms.core.system.entity.User;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Lob;
import java.util.Date;

public class ArticleBaseDto {

    private Long id;
    /**
     * 标题
     */
    private String title;


    /**
     * 是否已发布
     */
    private boolean isPublished;

    /**
     * 是否已删除
     */
    private boolean isDeleted;

    /**
     * 链接
     */
    private String permaLink;

    /**
     * 摘要
     */
    private String summary;


    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 作者
     */
    private UserDto author;


    /**
     * 发布日期
     */
    private Date publishedDate;


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



    public boolean getIsPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
