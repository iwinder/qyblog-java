package com.windcoder.qycms.blog.entity;

import com.windcoder.qycms.system.entity.Auditable;
import java.util.Date;

public class BlogArticle extends Auditable {
    private Long id;

    private String title;

    private String permaLink;

    private String canonicalLink;

    private String summary;

    private String thumbnail;

    private Integer type;

    private Long viewCount;

    private Date publishedDate;

    private Long authorId;

    private Long categoryId;

    private Long commentAgentId;

    private Boolean deleted;

    private Boolean published;


    private String content;

    private String contentHtml;

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

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public String getCanonicalLink() {
        return canonicalLink;
    }

    public void setCanonicalLink(String canonicalLink) {
        this.canonicalLink = canonicalLink;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCommentAgentId() {
        return commentAgentId;
    }

    public void setCommentAgentId(Long commentAgentId) {
        this.commentAgentId = commentAgentId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", permaLink=").append(permaLink);
        sb.append(", canonicalLink=").append(canonicalLink);
        sb.append(", summary=").append(summary);
        sb.append(", thumbnail=").append(thumbnail);
        sb.append(", type=").append(type);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", publishedDate=").append(publishedDate);
        sb.append(", authorId=").append(authorId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", commentAgentId=").append(commentAgentId);
        sb.append(", deleted=").append(deleted);
        sb.append(", published=").append(published);
        sb.append(", createdBy=").append(super.getCreatedBy());
        sb.append(", lastModifiedBy=").append(super.getLastModifiedBy());
        sb.append(", createdDate=").append(super.getCreatedDate());
        sb.append(", lastModifiedDate=").append(super.getLastModifiedDate());
        sb.append(", content=").append(content);
        sb.append(", contentHtml=").append(contentHtml);
        sb.append("]");
        return sb.toString();
    }
}