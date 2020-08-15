package com.windcoder.qycms.blog.dto;

import lombok.Data;

@Data
public class BlogCategoryWebDto {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 关键词
     */
    private String identifier;
}
