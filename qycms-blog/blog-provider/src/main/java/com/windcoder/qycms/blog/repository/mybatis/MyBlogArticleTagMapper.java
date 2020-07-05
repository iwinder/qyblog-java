package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.entity.BlogArticleTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBlogArticleTagMapper {
    int deleteByArticleIdAndTagIdBatch(@Param("articleTagList") List<BlogArticleTag> articleTagList);
    int insterBatch(@Param("articleTagList") List<BlogArticleTag> articleTagList);
    List<String> findTagnameListByArticleId(@Param("articleId") Long articleId);
}
