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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogArticleService extends BaseService<BlogArticle,Long, BlogArticleRepository> {

    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询文章列表-分页
     * @param article
     * @param pageable
     * @return
     */
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

    /**
     * 保存
     * @param article
     * @return
     */
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

    /**
     * 更新
     * @param article
     * @return
     */
    public BlogArticle update(BlogArticle article) {
        stringToTags(article);
        return super.save(article);
    }

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    public BlogArticle findInfo(Long id){
        BlogArticle article =  repository.findByIdAndIsDeletedAndIsPublished(id,false,true);
        return article;
    }

    /**
     * 处理Tag
     * @param article
     */
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
