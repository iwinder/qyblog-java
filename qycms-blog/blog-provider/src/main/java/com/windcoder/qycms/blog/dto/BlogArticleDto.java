package com.windcoder.qycms.blog.dto;

import com.windcoder.qycms.core.basis.comment.dto.CommentAgentDto;
import com.windcoder.qycms.core.basis.comment.dto.SystemCommentSettingDto;

public class BlogArticleDto extends BlogArticleBaseDto {

    /**
     * 内容-mkdown
     */
    private String content;
    /**
     * 内容-html
     */
    private String contentHtml;

    private SystemCommentSettingDto systemCommentSetting;

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

    public SystemCommentSettingDto getSystemCommentSetting() {
        return systemCommentSetting;
    }

    public void setSystemCommentSetting(SystemCommentSettingDto systemCommentSetting) {
        this.systemCommentSetting = systemCommentSetting;
    }
}
