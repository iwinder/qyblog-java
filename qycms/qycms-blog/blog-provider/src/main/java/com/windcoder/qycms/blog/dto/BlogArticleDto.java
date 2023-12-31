package com.windcoder.qycms.blog.dto;

public class BlogArticleDto extends BlogArticleBaseDto {

    /**
     * 内容-mkdown
     */
    private String content;
    /**
     * 内容-html
     */
    private String contentHtml;

    private String oldUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getOldUrl() {
        return oldUrl;
    }

    public void setOldUrl(String oldUrl) {
        this.oldUrl = oldUrl;
    }
}
