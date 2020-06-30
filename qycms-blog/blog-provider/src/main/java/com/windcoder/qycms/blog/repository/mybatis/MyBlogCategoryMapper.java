package com.windcoder.qycms.blog.repository.mybatis;

import org.apache.ibatis.annotations.Param;

public interface MyBlogCategoryMapper {
    void updatePathChildren(@Param("parentId")Long parentId, @Param("parentIdPath") String parentIdPath,
                            @Param("parentNamePath")String parentNamePath);
}
