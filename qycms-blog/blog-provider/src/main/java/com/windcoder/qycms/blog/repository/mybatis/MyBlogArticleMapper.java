package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBlogArticleMapper {
    List<BlogArticleBaseDto> list(@Param("pageDto") BlogArticlePageDto pageDto);
    void updateDeleted(@Param("deletedStatus") Boolean deletedStatus, @Param("ids")Long[] ids);
}
