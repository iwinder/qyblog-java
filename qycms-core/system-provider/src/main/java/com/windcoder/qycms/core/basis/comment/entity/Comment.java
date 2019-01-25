package com.windcoder.qycms.core.basis.comment.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.windcoder.qycms.core.system.entity.Auditable;
import com.windcoder.qycms.core.system.entity.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="sns_comment")
public class Comment implements Serializable {

//    public interface SimpleView extends Auditable.BaseView {};
    @Id
    @GeneratedValue
//    @JsonView(SimpleView.class)
    private Long id;

    @Lob
//    @JsonView(SimpleView.class)
    private String content;

    /**
     * 评论者
     */
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
     * APPLIED 待审核
     * ENROLLED 审核通过
     * REFUSED 拒绝---丢入垃圾箱
     */
    @ColumnDefault("'APPLIED'")
    private String status;

    /**
     * 评论者客户端
     */
    private String agent;

    /**
     * 评论代理
     */
    @ManyToOne
    @JoinColumn(name="target_id")
    private CommentAgent target;

    /**
     * 层级
     */
    private Integer depth;

    /**
     * 父级评论
     */
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Comment parent;

    /**
     * 根级评论
     */
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

    @CreatedDate
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(updatable=false)
    private Date createdDate;

    @LastModifiedDate
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date lastModifiedDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public CommentAgent getTarget() {
        return target;
    }

    public void setTarget(CommentAgent target) {
        this.target = target;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Comment getTopParent() {
        return topParent;
    }

    public void setTopParent(Comment topParent) {
        this.topParent = topParent;
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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
