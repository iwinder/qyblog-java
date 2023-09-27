package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.entity.BlogArticleTag;
import com.windcoder.qycms.blog.entity.BlogArticleTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogArticleTagMapper {
    long countByExample(BlogArticleTagExample example);

    int deleteByExample(BlogArticleTagExample example);

    int deleteByPrimaryKey(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

    int insert(BlogArticleTag record);

    int insertSelective(BlogArticleTag record);

    List<BlogArticleTag> selectByExample(BlogArticleTagExample example);

    int updateByExampleSelective(@Param("record") BlogArticleTag record, @Param("example") BlogArticleTagExample example);

    int updateByExample(@Param("record") BlogArticleTag record, @Param("example") BlogArticleTagExample example);
}