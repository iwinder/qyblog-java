package com.windcoder.qycms.blog.repository.jpa;


import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.repository.SupportRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogArticleRepository extends SupportRepository<BlogArticle,Long> {
    BlogArticle findByIdAndIsDeletedAndIsPublished(Long id,boolean isDeleted,boolean isPublished);

    @Query("select viewCount from BlogArticle ba where ba.id = ?1")
    Long findViewCountById(@Param("articleId") Long articleId);
}
