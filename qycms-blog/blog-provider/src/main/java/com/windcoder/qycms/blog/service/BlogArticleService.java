package com.windcoder.qycms.blog.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.blog.entity.BlogArticleExample;
import com.windcoder.qycms.blog.repository.mybatis.BlogArticleMapper;
//import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.blog.repository.mybatis.MyBlogArticleMapper;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.utils.CopyUtil;
//import org.apache.shiro.SecurityUtils;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BlogArticleService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;
    @Autowired
    private MyBlogArticleMapper myBlogArticleMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void findAll(BlogArticlePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
//        BlogArticleExample articleExample = new BlogArticleExample();
        List<BlogArticleBaseDto> articles = myBlogArticleMapper.list(pageDto);
        PageInfo<BlogArticleBaseDto> pageInfo = new PageInfo<>(articles);
        pageDto.setTotal(pageInfo.getTotal());
//        List<BlogArticleBaseDto> userDtoList = CopyUtil.copyList(articles, BlogArticleBaseDto.class);
        pageDto.setList(articles);



//        return super.findAll((root, query,  cb) -> {
//            Predicate predicate = cb.equal(root.get("isDeleted"), false);
//            if(article.getTitle() != null) {
//                predicate = cb.and(predicate, cb.like( cb.lower(root.get("title")),
//                        "%"+ StringUtils.trim(article.getTitle()).toLowerCase()+"%" ));
//            }
//            if(article.getIsPublished() != null) {
//                predicate  = cb.and(predicate, cb.equal(root.get("isPublished"),article.getIsPublished()));
//            }
//            return predicate;
//
//        },pageable);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param articleDto
     */
    @Transactional
    public void save(BlogArticleDto articleDto) {
//        BlogArticle article = CopyUtil.copy(articleDto, BlogArticle.class);
        BlogArticle article = ModelMapperUtils.map(articleDto, BlogArticle.class);
//        if (null == article.getAuthor()){
//            User user = (User)  SecurityUtils.getSubject().getPrincipal();
//            article.setAuthor(user);
//        }BlogArticle
        if (article.getPublished() &&  null == article.getPublishedDate()){
            article.setPublishedDate(new Date());
        }
        stringToTags(article);
        if (article.getId() == null) {
            this.inster(article);
        } else {
            this.update(article);
        }
    }

    /**
     * 新增
     * @param article
     */
    private void inster(BlogArticle article){
        Date now = new Date();
        article.setCreatedDate(now);
        article.setLastModifiedDate(now);
        blogArticleMapper.insert(article);
    }

    /**
     * 更新
     * @param article
     */
    private void update(BlogArticle article) {
        article.setLastModifiedDate(new Date());
        blogArticleMapper.updateByPrimaryKey(article);
    }

    public void stringToTags(BlogArticle article){
//        if (article.getTagStrings() == null){
//            return;
//        }
//        BlogTag tag = null;
//        List<BlogTag> newTags = new ArrayList<BlogTag>();
//        for (String tagStr: article.getTagStrings()) {
//            tag = blogTagService.findByName(tagStr);
//            if (tag == null){
//                tag = new BlogTag();
//                tag.setName(tagStr);
//                tag  =  blogTagService.save(tag);
//            }
//            newTags.add(tag);
//        }
//        article.setTags(newTags);
    }


    public BlogArticle findOne(Long articleId) {
        BlogArticle article = blogArticleMapper.selectByPrimaryKey(articleId);
        return article;
    }

    public void delete(Long[] ids) {
       myBlogArticleMapper.updateDeleted(true,ids);
    }
}
