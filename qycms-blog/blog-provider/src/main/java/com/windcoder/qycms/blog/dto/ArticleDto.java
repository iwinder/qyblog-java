package com.windcoder.qycms.blog.dto;

public class ArticleDto extends ArticleBaseDto {

    /**
     * 内容-mkdown
     */
    private String content;
    /**
     * 内容-html
     */
    private String contentHtml;

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

}
