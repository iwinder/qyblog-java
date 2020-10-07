package com.windcoder.qycms.system.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentDto {

    /**
     * id
     */
    private Long id;

    /**
     * 评论者客户端
     */
    private String agent;

    /**
     * 评论者用户名
     */
    private String authorName;

    /**
     * 评论者邮箱
     */
    private String authorEmail;

    /**
     * 评论者ip
     */
    private String authorIp;

    /**
     * 评论者网址
     */
    private String authorUrl;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论状态
     */
    private String status;

    /**
     * 评论代理
     */
    private Long targetId;

    /**
     * 评论层级
     */
    private Integer depth;

    /**
     * 父评论
     */
    private Long parentId;

    /**
     * 用户id, 0 游客
     */

    private UserWebDto user;

    /**
     * 根回复
     */
    private Long topParentId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifiedDate;

    private String targetName;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorIp() {
        return authorIp;
    }

    public void setAuthorIp(String authorIp) {
        this.authorIp = authorIp;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public Long getTopParentId() {
        return topParentId;
    }

    public void setTopParentId(Long topParentId) {
        this.topParentId = topParentId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserWebDto getUser() {
        return user;
    }

    public void setUser(UserWebDto user) {
        this.user = user;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", agent=").append(agent);
        sb.append(", authorName=").append(authorName);
        sb.append(", authorEmail=").append(authorEmail);
        sb.append(", authorIp=").append(authorIp);
        sb.append(", authorUrl=").append(authorUrl);
        sb.append(", content=").append(content);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", targetId=").append(targetId);
        sb.append(", targetName=").append(targetName);
        sb.append(", depth=").append(depth);
        sb.append(", parentId=").append(parentId);
        sb.append(", user=").append(user);
        sb.append(", topParentId=").append(topParentId);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append("]");
        return sb.toString();
    }


}