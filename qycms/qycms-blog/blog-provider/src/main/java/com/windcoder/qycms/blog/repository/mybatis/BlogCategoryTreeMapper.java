package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.entity.BlogCategoryTree;
import com.windcoder.qycms.blog.entity.BlogCategoryTreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogCategoryTreeMapper {
    long countByExample(BlogCategoryTreeExample example);

    int deleteByExample(BlogCategoryTreeExample example);

    int deleteByPrimaryKey(@Param("parentId") Long parentId, @Param("childId") Long childId);

    int insert(BlogCategoryTree record);

    int insertSelective(BlogCategoryTree record);

    List<BlogCategoryTree> selectByExample(BlogCategoryTreeExample example);

    BlogCategoryTree selectByPrimaryKey(@Param("parentId") Long parentId, @Param("childId") Long childId);

    int updateByExampleSelective(@Param("record") BlogCategoryTree record, @Param("example") BlogCategoryTreeExample example);

    int updateByExample(@Param("record") BlogCategoryTree record, @Param("example") BlogCategoryTreeExample example);

    int updateByPrimaryKeySelective(BlogCategoryTree record);

    int updateByPrimaryKey(BlogCategoryTree record);
}