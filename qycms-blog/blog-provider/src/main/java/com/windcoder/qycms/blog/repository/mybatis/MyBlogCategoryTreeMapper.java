package com.windcoder.qycms.blog.repository.mybatis;

import com.windcoder.qycms.blog.entity.BlogCategoryTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBlogCategoryTreeMapper {
    void updateChildCount(@Param("childId") Long childId);
    void inserOrUpdateBatch(@Param("categoryTreeList") List<BlogCategoryTree> categoryTreeList);

    void cleanParents(@Param("id")Long id);

    void minusChildCount(@Param("childId")Long childId);

    Long selectChildrenCount(@Param("childId") Long childId);
}
