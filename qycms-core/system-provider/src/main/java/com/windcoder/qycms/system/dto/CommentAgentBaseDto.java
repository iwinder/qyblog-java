package com.windcoder.qycms.system.dto;

public class CommentAgentBaseDto {
    /**
     * id
     */
    private Long id;

    /**
     * 评论对象id
     */
    private Long targetId;

    /**
     * 评论对象类型
     */
    private String targetType;

    /**
     * 评论对象名称
     */
    private String targetName;


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

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
