package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBlogArticleMapper {
    List<BlogArticleBaseDto> list(@Param("pageDto") BlogArticlePageDto pageDto);
    List<BlogArticleWebBaseDto> listWeb(@Param("pageDto") BlogArticlePageDto pageDto);
    BlogArticleWebDto findOneWeb(@Param("articleDto") BlogArticleDto blogArticleDto);
    void updateDeleted(@Param("deletedStatus") Boolean deletedStatus, @Param("ids")Long[] ids);
    Integer countPermaLinkLike(@Param("permaLink") String permaLink, @Param("notId")Long notId);
    Integer countPermaLinkEquals(@Param("permaLink") String permaLink, @Param("notId")Long notId);
}
