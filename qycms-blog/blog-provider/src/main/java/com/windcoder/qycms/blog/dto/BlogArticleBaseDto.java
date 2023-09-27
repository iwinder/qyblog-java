package com.windcoder.qycms.blog.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.windcoder.qycms.system.dto.UserWebDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogArticleBaseDto {

    private Long id;
    /**
     * 标题
     */
    private String title;


    /**
     * 是否已发布
     */
    private Boolean published;

    /**
     * 是否已删除
     */
    private Boolean deleted;

    private String password;

    private String status;

    /**
     * 链接
     */
    private String permaLink;
    /**
     * 规范链接
     */
    private String canonicalLink;

    /**
     * 摘要
     */
    private String summary;


    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 作者
     */
    private UserWebDto author;
//
    private BlogCategoryDto category;


    private List<String> tagStrings;
    /**
     * 发布日期
     */
    private Date publishedDate;

    private Long commentAgentId;

    private Integer type;

    private Long viewCount;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifiedDate;



}
