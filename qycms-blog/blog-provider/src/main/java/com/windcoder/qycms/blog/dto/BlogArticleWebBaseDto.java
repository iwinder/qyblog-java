package com.windcoder.qycms.blog.dto;

import com.windcoder.qycms.system.dto.UserWebDto;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class BlogArticleWebBaseDto {

    private Long id;
    /**
     * 标题
     */
    private String title;


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
    private BlogCategoryWebDto category;


    private List<String> tagStrings;
    private List<BlogTagWebDto> tags;
    /**
     * 发布日期
     */
    private Date publishedDate;

    private Long commentAgentId;

    private Integer type;

    private Long viewCount;
    private Long commentCount;
    private String defNum;
}
