package com.windcoder.qycms.system.dto;

import com.windcoder.qycms.dto.PageDto;

public class CommentPageDto extends PageDto {
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
