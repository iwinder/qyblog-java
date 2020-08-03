package com.windcoder.qycms.blog.dto;

//import com.windcoder.qycms.core.system.dto.CategoryBaseDto;
//import com.windcoder.qycms.core.system.dto.UserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogArticleBaseDto {

    private Long id;
    /**
     * 标题
     */
    private String title;


    /**
     * 是否已发布
     */
    private Boolean published;

    /**
     * 是否已删除
     */
    private Boolean deleted;

    /**
     * 链接
     */
    private String permaLink;
    /**
     * 规范链接
     */
    private String canonicalLink;

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
//    private UserDto author;
//
    private BlogCategoryDto category;


    private List<String> tagStrings;
    /**
     * 发布日期
     */
    private Date publishedDate;

    private Long commentAgentId;

    private Integer type;


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


    public Boolean getPublished() {
        return published;
    }


    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.deleted = isDeleted;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

//    public UserDto getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(UserDto author) {
//        this.author = author;
//    }


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

    public BlogCategoryDto getCategory() {
        return category;
    }

    public void setCategory(BlogCategoryDto category) {
        this.category = category;
    }



    public List<String> getTagStrings() {
        return tagStrings;
    }

    public void setTagStrings(List<String> tagStrings) {
        this.tagStrings = tagStrings;
    }

    public Long getCommentAgentId() {
        return commentAgentId;
    }

    public void setCommentAgentId(Long commentAgentId) {
        this.commentAgentId = commentAgentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCanonicalLink() {
        return canonicalLink;
    }

    public void setCanonicalLink(String canonicalLink) {
        this.canonicalLink = canonicalLink;
    }
}
