package com.windcoder.qycms.blog.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.blog.entity.*;
import com.windcoder.qycms.blog.repository.mybatis.BlogArticleMapper;
//import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.blog.repository.mybatis.MyBlogArticleMapper;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.utils.CopyUtil;
//import org.apache.shiro.SecurityUtils;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.criteria.Predicate;
import javax.annotation.Resource;
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
    @Resource
    private BlogCategoryService blogCategoryService;
    @Autowired
    private BlogTagService blogTagService;

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
        List<String> tagsString = articleDto.getTagStrings();
        BlogArticle article = ModelMapperUtils.map(articleDto, BlogArticle.class);
//        if (null == article.getAuthor()){
//            User user = (User)  SecurityUtils.getSubject().getPrincipal();
//            article.setAuthor(user);
//        }BlogArticle
        if (article.getPublished() &&  null == article.getPublishedDate()){
            article.setPublishedDate(new Date());
        }

        if (article.getId() == null) {
            this.inster(article);
        } else {
            this.update(article);
        }

        saveTags(article.getId(), tagsString);

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
        blogArticleMapper.updateByPrimaryKeySelective(article);
    }

    public void saveTags(Long articleId, List<String> tagsString){
        if(tagsString ==null || tagsString.isEmpty()) {
            return;
        }
        BlogTag tag = null;
        BlogArticleTag articleTag = null;
        List<BlogArticleTag> oldArticleTags = blogTagService.findArticleTagByArticleId(articleId);
        List<BlogArticleTag> newArticleTags = new ArrayList<BlogArticleTag>();
        List<BlogTag> unchangedTags = new ArrayList<BlogTag>();
        for (String tagStr: tagsString) {
            tag = blogTagService.findByName(tagStr);
            if (tag == null){
                tag = new BlogTag();
                tag.setName(tagStr);
                blogTagService.save(tag);
            }  else {
                unchangedTags.add(tag);
            }
            articleTag = new BlogArticleTag(articleId, tag.getId());
            newArticleTags.add(articleTag);
        }
        if (!oldArticleTags.isEmpty()) {
            List<BlogArticleTag> deleedArticleTags = new ArrayList<BlogArticleTag>();
            Boolean flag = true;
            for (BlogArticleTag at: oldArticleTags) {
                flag = true;
                for (BlogTag ucTag: unchangedTags) {
                    if(at.getTagId().equals(ucTag.getId())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    deleedArticleTags.add(at);
                }
            }
            if (!deleedArticleTags.isEmpty()) {
                blogTagService.deleteBlogArticleTags(deleedArticleTags);
            }

        }
        blogTagService.insterBlogArticleTagBatch(newArticleTags);

    }


    public BlogArticle findOne(Long articleId) {
        BlogArticle article = blogArticleMapper.selectByPrimaryKey(articleId);
        return article;
    }

    public void delete(Long[] ids) {
       myBlogArticleMapper.updateDeleted(true,ids);
    }

    public BlogArticleDto findOneArticleDto(Long articleId) {
        BlogArticleExample example = new BlogArticleExample();
        example.createCriteria().andDeletedEqualTo(false).andIdEqualTo(articleId);
        List<BlogArticle> blogArticles =  blogArticleMapper.selectByExampleWithBLOBs(example);
        if (blogArticles.isEmpty()) {
            throw new BusinessException("非法的数据请求");
        }
        BlogArticle article = blogArticles.get(0);
        BlogArticleDto articleDto = ModelMapperUtils.map(article, BlogArticleDto.class);
        if (article.getCategoryId() != null) {
            BlogCategoryDto categoryDto =  blogCategoryService.findOneCategoryDto(article.getCategoryId());
            articleDto.setCategory(categoryDto);
        }
        List<String> tagNameList =  blogTagService.findTagnameListByArticleId(articleDto.getId());
        articleDto.setTagStrings(tagNameList);
        return articleDto;
    }

//    private  void articleBaseDtoTofill(List<BlogArticleBaseDto> baseDtos) {
//        for (BlogArticleBaseDto baseDto: baseDtos) {
//            List<String> tagNameList =  blogTagService.findTagnameListByArticleId(baseDto.getId());
//            baseDto.setTagStrings(tagNameList);
//        }
//    }
}
