package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebBaseDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBlogArticleMapper {
    List<BlogArticleBaseDto> list(@Param("pageDto") BlogArticlePageDto pageDto);
    List<BlogArticleWebBaseDto> listWeb(@Param("pageDto") BlogArticlePageDto pageDto);
    void updateDeleted(@Param("deletedStatus") Boolean deletedStatus, @Param("ids")Long[] ids);
}
