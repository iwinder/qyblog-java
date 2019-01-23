package com.windcoder.qycms.core.basis.comment.dto;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;

public class CommentAgentDto {
    private Long id;
    /**
     * 评论对象id
     */
    private Long targetId;

    /**
     * 评论总数
     */

    private Integer commentCount;

    /**
     * 评论是否可用
     */

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
