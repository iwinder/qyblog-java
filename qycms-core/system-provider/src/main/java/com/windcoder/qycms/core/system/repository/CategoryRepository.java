package com.windcoder.qycms.core.system.repository;

import com.windcoder.qycms.core.system.entity.Category;
import com.windcoder.qycms.repository.SupportRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CategoryRepository  extends SupportRepository<Category,Long> {
    @Query("select Max(category.displayOrder) from Category category where category.parent.id = ?1")
    Long getMaxDisplayOrderByParentId(Long id);
    @Query("select Max(category.displayOrder) from Category category where category.parent is null ")
    Long getMaxDisplayOrderByParentIdIsNull();

    @Transactional
    @Modifying
    @Query(value="update Category c set c.idPath = concat(:parentIdPath, c.id), c.titlePath = concat(:parentTitlePath, c.title)"
            + "where c.parent.id = :parentId")
    void updatePathChildren(@Param("parentId")Long parentId, @Param("parentIdPath") String parentIdPath,
                            @Param("parentTitlePath")String parentTitlePath);
}
