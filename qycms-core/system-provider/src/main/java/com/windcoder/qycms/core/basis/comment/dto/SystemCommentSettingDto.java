package com.windcoder.qycms.core.basis.comment.dto;

import org.hibernate.annotations.ColumnDefault;

public class SystemCommentSettingDto {
    private Long id;
    /**
     * 是否可用
     */
    @ColumnDefault("1")
    private Boolean isEnabled;
}
