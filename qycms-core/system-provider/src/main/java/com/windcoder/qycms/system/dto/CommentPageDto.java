package com.windcoder.qycms.system.dto;

import com.windcoder.qycms.dto.PageDto;

public class CommentPageDto extends PageDto {
    private Long parentId;

    /**
     * APPLIED 待审核
     * ENROLLED 通过
     * REFUSED 拒绝
     */
    private String status;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
