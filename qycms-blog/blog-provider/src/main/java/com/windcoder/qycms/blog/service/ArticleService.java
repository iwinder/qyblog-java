package com.windcoder.qycms.blog.service;

import com.windcoder.qycms.blog.entity.Article;
import com.windcoder.qycms.blog.repository.ArticleRepository;
import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Date;

@Service
public class ArticleService extends BaseService<Article,Long, ArticleRepository> {
    public Page<Article> findAll(Article article, Pageable pageable) {
        return super.findAll((root, query,  cb) -> {
            Predicate predicate = cb.equal(root.get("isDeleted"), false);
            if(article.getTitle() != null) {
                predicate = cb.and(predicate, cb.like( cb.lower(root.get("title")),
                        "%"+ StringUtils.trim(article.getTitle()).toLowerCase()+"%" ));
            }
            if(article.getIsPublished() != null) {
                predicate  = cb.and(predicate, cb.equal(root.get("isPublished"),article.getIsPublished()));
            }
            return predicate;

        },pageable);
    }


    public Article save(Article article) {
        if (null == article.getAuthor()){
            User user = (User)  SecurityUtils.getSubject().getPrincipal();
            article.setAuthor(user);
        }
        if (article.getIsPublished() &&  null == article.getPublishedDate()){
            article.setPublishedDate(new Date());
        }
        return super.save(article);
    }

    public Article update(Article article) {
        return super.save(article);
    }
}
