package com.windcoder.qycms.blog.service;


import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.repository.BlogArticleRepository;
import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogArticleService extends BaseService<BlogArticle,Long, BlogArticleRepository> {

    @Autowired
    private BlogTagService blogTagService;

    public Page<BlogArticle> findAll(BlogArticle article, Pageable pageable) {
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


    public BlogArticle save(BlogArticle article) {
        if (null == article.getAuthor()){
            User user = (User)  SecurityUtils.getSubject().getPrincipal();
            article.setAuthor(user);
        }
        if (article.getIsPublished() &&  null == article.getPublishedDate()){
            article.setPublishedDate(new Date());
        }
        stringToTags(article);
        return super.save(article);
    }

    public BlogArticle update(BlogArticle article) {
        stringToTags(article);
        return super.save(article);
    }

    public void stringToTags(BlogArticle article){
        if (article.getTagStrings() == null){
            return;
        }
        BlogTag tag = null;
        List<BlogTag> newTags = new ArrayList<BlogTag>();
        for (String tagStr: article.getTagStrings()) {
            tag = blogTagService.findByName(tagStr);
            if (tag == null){
                tag = new BlogTag();
                tag.setName(tagStr);
                tag  =  blogTagService.save(tag);
            }
            newTags.add(tag);
        }
        article.setTags(newTags);
    }


}
