package com.windcoder.qycms.system.dto;

import lombok.Data;

@Data
public class ShortLinkWebDto {
    /**
     * id
     */
    private Long id;

    /**
     * 短链接
     */
    private String identifier;

    /**
     * 链接地址
     */
    private String url;
}
