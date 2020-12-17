package com.windcoder.qycms.blog.dto;

import lombok.Data;

@Data
public class BlogArticleWebDto extends BlogArticleWebBaseDto{
    /**
     * 内容-html
     */
    private String contentHtml;
    private String content;
    private Boolean commentAgentFlag;
}
