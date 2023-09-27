package com.windcoder.qycms.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CommentListDto {
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
    private CommentAgentBaseDto target;

    /**
     * 评论层级
     */
    private Integer depth;

    /**
     * 父评论
     */
    private CommentDto parent;

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

    public CommentAgentBaseDto getTarget() {
        return target;
    }

    public void setTarget(CommentAgentBaseDto target) {
        this.target = target;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public CommentDto getParent() {
        return parent;
    }

    public void setParent(CommentDto parent) {
        this.parent = parent;
    }

    public UserWebDto getUser() {
        return user;
    }

    public void setUser(UserWebDto user) {
        this.user = user;
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

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public String toString() {
        return "CommentListDto{" +
                "id=" + id +
                ", agent='" + agent + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", authorIp='" + authorIp + '\'' +
                ", authorUrl='" + authorUrl + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", target=" + target +
                ", depth=" + depth +
                ", parent=" + parent +
                ", user=" + user +
                ", topParentId=" + topParentId +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", targetName='" + targetName + '\'' +
                '}';
    }
}
