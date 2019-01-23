package com.windcoder.qycms.core.basis.comment.entity;

import com.windcoder.qycms.core.system.entity.Auditable;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sns_comment_agent")
public class CommentAgent extends Auditable {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 评论对象id
     */
    private Long targetId;
    /**
     * 评论对象名称
     */
    private String targetName;

    /**
     * 评论总数
     */
    @Formula("(select count(scm.id) from sns_comment scm where scm.target_id = id)")
    private Integer commentCount;

    /**
     * 评论是否可用
     */
    @ColumnDefault("1")
    private Boolean isEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }
}
