package com.windcoder.qycms.blog.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.windcoder.qycms.basis.utils.PinyinUtilZ;
import com.windcoder.qycms.blog.dto.*;
import com.windcoder.qycms.blog.entity.*;
import com.windcoder.qycms.blog.repository.mybatis.BlogArticleMapper;

import com.windcoder.qycms.blog.repository.mybatis.MyBlogArticleMapper;

import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.annotation.ViewCountLimit;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.dto.UserWebDto;
import com.windcoder.qycms.system.entity.CommentAgent;
import com.windcoder.qycms.system.enums.CommenttTargetType;
import com.windcoder.qycms.system.service.CommentAgentService;

import com.windcoder.qycms.utils.IpAddressUtil;
import com.windcoder.qycms.utils.ModelMapperUtils;
import com.windcoder.qycms.utils.StringUtilZ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @Autowired
    private CommentAgentService commentAgentService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 列表查询
     * @param pageDto
     */
    public void findAll(BlogArticlePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        List<BlogArticleBaseDto> articles = myBlogArticleMapper.list(pageDto);
        PageInfo<BlogArticleBaseDto> pageInfo = new PageInfo<>(articles);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(articles);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param articleDto
     */
    @Transactional
    public void save(BlogArticleDto articleDto, UserWebDto user) {
//        BlogArticle article = CopyUtil.copy(articleDto, BlogArticle.class);
        List<String> tagsString = articleDto.getTagStrings();
        BlogArticle article = ModelMapperUtils.map(articleDto, BlogArticle.class);
//        if (null == article.getAuthorId()){
//            User user = (User)  SecurityUtils.getSubject().getPrincipal();
//            article.setAuthorId(user);
//        }
        if (article.getPublished() &&  null == article.getPublishedDate()){
            article.setPublishedDate(new Date());
        }

        if (article.getId() == null) {
            CommentAgent agent = initCommentAgent(article);
            article.setCommentAgentId(agent.getId());
            if(StringUtils.isBlank(article.getPermaLink())) {
                article.setPermaLink(getNewPermaLinkLikeByTitle(article.getTitle()));
            }
            if (article.getAuthorId()==null) {
                article.setAuthorId(user.getId());
            }
            article.setViewCount(0L);
            article.setSummary(StringUtilZ.removeHtmlAndSubstring(article.getContentHtml()));
            this.inster(article);
        } else {
            article.setViewCount(null);
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

    public String checkAndGetPermaLink(BlogArticleDto article) {
        if (StringUtils.isBlank(article.getPermaLink()) && StringUtils.isBlank(article.getTitle())) {
            throw new BusinessException("参数异常");
        }
        String titile = article.getPermaLink();
        if (StringUtils.isBlank(article.getPermaLink())) {
            titile = PinyinUtilZ.toHanYuPinyinString(article.getTitle());
        }
        return getNewPermaLinkLikeByLink(titile, article.getId());
    }

    public String getNewPermaLinkLikeByLink(String link,Long notId) {
        Integer count = countPermaLinkLike(link, notId);
        if (count >0) {
            StringBuilder str = new StringBuilder(link).append("-").append(count+1);
            return str.toString();
        }
        return link;
    }
    /**
     * 获取新的PermaLink
     * @param title
     * @return
     */
    public String getNewPermaLinkLikeByTitle(String title) {
        String titile = PinyinUtilZ.toHanYuPinyinString(title);
        return getNewPermaLinkLikeByLink(titile, null);
    }
    public Integer getPermaLinkLikeByTitle(String title, Long notId) {
        String titile = PinyinUtilZ.toHanYuPinyinString(title);
        return myBlogArticleMapper.countPermaLinkLike(titile, notId);
    }

    public Integer countPermaLinkLike(String permaLink, Long notId) {
       return myBlogArticleMapper.countPermaLinkLike(permaLink, notId);
    }
    public CommentAgent initCommentAgent(BlogArticle article) {
        CommentAgent agent = new CommentAgent();
        agent.setEnabled(true);
        agent.setTargetType(article.getType().equals(1)?CommenttTargetType.ARTICLE.name():CommenttTargetType.PAGE.name());
        agent.setTargetName(article.getTitle());
        if (article.getId()!=null) {
            agent.setTargetId(article.getId());
        }
        commentAgentService.save(agent);
        return agent;

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

    public BlogArticle findOne(BlogArticleDto blogArticleDto) {
        BlogArticleExample example = new BlogArticleExample();
        BlogArticleExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(blogArticleDto.getId()!=null) {
            criteria.andIdEqualTo(blogArticleDto.getId());
        }
        if (blogArticleDto.getPublished()!=null) {
            criteria.andPublishedEqualTo(blogArticleDto.getPublished());
        }
        if (StringUtils.isNotBlank(blogArticleDto.getPermaLink())) {
            criteria.andPermaLinkEqualTo(blogArticleDto.getPermaLink());
        }
        List<BlogArticle> blogArticles =  blogArticleMapper.selectByExampleWithBLOBs(example);
        if (blogArticles.isEmpty()) {
            throw new BusinessException("非法的数据请求");
        }
        return blogArticles.get(0);
    }

    public BlogArticleDto findOneArticleDto(BlogArticleDto blogArticleDto) {
        BlogArticle article = findOne(blogArticleDto);
        BlogArticleDto articleDto = ModelMapperUtils.map(article, BlogArticleDto.class);
        if (article.getCategoryId() != null) {
            BlogCategoryDto categoryDto =  blogCategoryService.findOneCategoryDto(article.getCategoryId());
            articleDto.setCategory(categoryDto);
        }
        List<String> tagNameList =  blogTagService.findTagnameListByArticleId(articleDto.getId());
        articleDto.setTagStrings(tagNameList);
        return articleDto;
    }

    public void findAllWebDto(BlogArticlePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        List<BlogArticleWebBaseDto> articles = myBlogArticleMapper.listWeb(pageDto);
        for (BlogArticleWebBaseDto article: articles) {
            article.setDefNum(String.valueOf(StringUtilZ.randomRange(1,32)));
        }
        PageInfo<BlogArticleWebBaseDto> pageInfo = new PageInfo<>(articles);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(articles);
    }

    @ViewCountLimit
    public BlogArticleWebDto findOneArticleWebDto(BlogArticleDto blogArticleDto) {
//        BlogArticle article = findOne(blogArticleDto);
        BlogArticleWebDto articleDto = myBlogArticleMapper.findOneWeb(blogArticleDto);
        if (articleDto==null) {
            throw  new BusinessException("404");
        }
        String value = IpAddressUtil.getClientRealIp();
        String key = new StringBuilder("post:viewCount:").append(articleDto.getId()).toString();
        redisUtil.addPostViewCount(key,value);
//        if (article.getCategoryId() != null) {
//            BlogCategoryDto categoryDto =  blogCategoryService.findOneCategoryDto(article.getCategoryId());
//            articleDto.setCategory(categoryDto);
//        }
//        if (articleDto.getType().intValue() == 1) {
//            List<String> tagNameList =  blogTagService.findTagnameListByArticleId(articleDto.getId());
//            articleDto.setTagStrings(tagNameList);
//        }
        Long nowCount = redisUtil.getPostViewCount(key);
        articleDto.setViewCount(nowCount + articleDto.getViewCount());
        articleDto.setDefNum(String.valueOf(StringUtilZ.randomRange(1,32)));
        return articleDto;
    }

    public void updateView(Long aid, Long viewCount) {
        BlogArticleExample example = new BlogArticleExample();
        myBlogArticleMapper.updatePostViews(aid, viewCount);
    }


//    private  void articleBaseDtoTofill(List<BlogArticleBaseDto> baseDtos) {
//        for (BlogArticleBaseDto baseDto: baseDtos) {
//            List<String> tagNameList =  blogTagService.findTagnameListByArticleId(baseDto.getId());
//            baseDto.setTagStrings(tagNameList);
//        }
//    }
}
