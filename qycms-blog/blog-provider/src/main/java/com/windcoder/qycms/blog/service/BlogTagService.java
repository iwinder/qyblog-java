package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.basis.utils.PinyinUtilZ;
import com.windcoder.qycms.blog.dto.BlogTagBaseDto;
import com.windcoder.qycms.blog.dto.BlogTagWebDto;
import com.windcoder.qycms.blog.entity.BlogArticleTag;
import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.entity.BlogTagExample;
import com.windcoder.qycms.blog.dto.BlogTagDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogTagMapper;
import com.windcoder.qycms.system.annotation.BloomIpLimit;
import com.windcoder.qycms.system.annotation.IpApiLimit;
import com.windcoder.qycms.utils.CopyUtil;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class BlogTagService {
    @Resource
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogArticleTagService blogArticleTagService;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        BlogTagExample blogTagExample = new BlogTagExample();
        List<BlogTag> blogTags = blogTagMapper.selectByExample(blogTagExample);
        PageInfo<BlogTag> pageInfo = new PageInfo<>(blogTags);
        pageDto.setTotal(pageInfo.getTotal());
        List<BlogTagDto> blogTagDtoList = CopyUtil.copyList(blogTags, BlogTagDto.class);
        pageDto.setList(blogTagDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param blogTag
     */
    public void save(BlogTag blogTag){
        if (blogTag.getId()==null) {
            if(StringUtils.isBlank(blogTag.getIdentifier())) {
                blogTag.setIdentifier(PinyinUtilZ.toHanYuPinyinString(blogTag.getName()));
            }
            this.inster(blogTag);
        } else {
            this.update(blogTag);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        BlogTagExample blogTagExample = new BlogTagExample();
        blogTagExample.createCriteria().andIdIn(Arrays.asList(ids));
        blogTagMapper.deleteByExample(blogTagExample);
    }

    /**
     * 新增
     * @param blogTag
     */
    private void inster(BlogTag blogTag){
        Date now = new Date();
        blogTag.setCreatedDate(now);
        blogTag.setLastModifiedDate(now);
        blogTagMapper.insertSelective(blogTag);
    }

    /**
     * 更新
     * @param blogTag
     */
    private void update(BlogTag blogTag){
        blogTag.setLastModifiedDate(new Date());
        blogTagMapper.updateByPrimaryKeySelective(blogTag);
    }

    public List<BlogTagBaseDto> search(BlogTagDto tagDto) {
        BlogTagExample example = new BlogTagExample();
        if(tagDto!=null && StringUtils.isNotBlank(tagDto.getName())) {
            example.createCriteria().andNameLike("%"+tagDto.getName() + "%");
        }
        List<BlogTag> blogTags = blogTagMapper.selectByExample(example);
        Type type = new TypeToken<List<BlogTagBaseDto>>() {}.getType();
        return ModelMapperUtils.map(blogTags,type);
    }

    public BlogTag findByName(String tagStr) {
        BlogTagExample example = new BlogTagExample();
        example.createCriteria().andNameEqualTo(tagStr);
        List<BlogTag> blogTags = blogTagMapper.selectByExample(example);
        if(!blogTags.isEmpty()) {
          return blogTags.get(0);
        }
        return null;
    }

    public BlogTag findById(Long id) {
        return blogTagMapper.selectByPrimaryKey(id);
    }

    public BlogTag findByIdentifier(String identifier) {
        BlogTagExample example = new BlogTagExample();
        example.createCriteria().andIdentifierEqualTo(identifier);
        List<BlogTag> blogTags = blogTagMapper.selectByExample(example);
        if(!blogTags.isEmpty()) {
            return blogTags.get(0);
        }
        return null;
    }

    public List<BlogArticleTag>  findArticleTagByArticleId(Long articleId) {
        return blogArticleTagService.findByArticleId(articleId);
    }

    public List<BlogArticleTag>  findArticleTagBytagId(Long tagId) {
        return blogArticleTagService.findByTagId(tagId);
    }

    public void deleteBlogArticleTags(List<BlogArticleTag> deleedArticleTags) {
        blogArticleTagService.deleteBatch(deleedArticleTags);
    }
    public void insterBlogArticleTagBatch(List<BlogArticleTag> articleTags) {
        blogArticleTagService.insterBatch(articleTags);
    }

    public List<String> findTagnameListByArticleId(Long articleId) {
        return blogArticleTagService.findTagnameListByArticleId(articleId);
    }
    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    public BlogTagWebDto findByIdForWeb(Long id) {
        BlogTag tag =  findById(id);
        if (tag==null) {
            return new BlogTagWebDto();
        }
        return ModelMapperUtils.map(tag, BlogTagWebDto.class);
    }
    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    public BlogTagWebDto findByIdentifierForWeb(String name) {
        BlogTag tag = findByIdentifier(name);
        if (tag==null) {
            return new BlogTagWebDto();
        }
        return ModelMapperUtils.map(tag, BlogTagWebDto.class);
    }

}
