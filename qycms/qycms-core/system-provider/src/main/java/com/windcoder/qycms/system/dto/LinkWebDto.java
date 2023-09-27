package com.windcoder.qycms.system.dto;

import lombok.Data;

@Data
public class LinkWebDto {
    /**
     * id
     */
    private Long id;

    /**
     * 链接名称
     */
    private String name;

    /**
     * 链接地址
     */
    private String url;


    /**
     * 描述
     */
    private String description;
}
