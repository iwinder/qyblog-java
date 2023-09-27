package com.windcoder.qycms.blog.dto;

import lombok.Data;

@Data
public class BlogTagWebDto {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;
    private String identifier;
}
