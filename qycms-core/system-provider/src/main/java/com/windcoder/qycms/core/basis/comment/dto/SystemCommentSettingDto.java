package com.windcoder.qycms.core.basis.comment.dto;

import org.hibernate.annotations.ColumnDefault;

public class SystemCommentSettingDto {
    private Long id;
    /**
     * 是否可用
     */

    private Boolean isEnabled;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }
}
