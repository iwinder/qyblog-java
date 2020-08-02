package com.windcoder.qycms.system.dto;

import com.windcoder.qycms.dto.PageDto;

public class MenusPageDto extends PageDto {
    private Long targetId;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
