package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.entity.BlogArticleVisitor;
import com.windcoder.qycms.blog.entity.BlogArticleVisitorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogArticleVisitorMapper {
    long countByExample(BlogArticleVisitorExample example);

    int deleteByExample(BlogArticleVisitorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BlogArticleVisitor record);

    int insertSelective(BlogArticleVisitor record);

    List<BlogArticleVisitor> selectByExample(BlogArticleVisitorExample example);

    BlogArticleVisitor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BlogArticleVisitor record, @Param("example") BlogArticleVisitorExample example);

    int updateByExample(@Param("record") BlogArticleVisitor record, @Param("example") BlogArticleVisitorExample example);

    int updateByPrimaryKeySelective(BlogArticleVisitor record);

    int updateByPrimaryKey(BlogArticleVisitor record);
}