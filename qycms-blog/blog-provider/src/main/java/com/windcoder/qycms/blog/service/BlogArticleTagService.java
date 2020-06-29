package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.blog.entity.BlogArticleTag;
import com.windcoder.qycms.blog.entity.BlogArticleTagExample;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogArticleTagMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class BlogArticleTagService {
    @Resource
    private BlogArticleTagMapper blogArticleTagMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        BlogArticleTagExample blogArticleTagExample = new BlogArticleTagExample();
        List<BlogArticleTag> blogArticleTags = blogArticleTagMapper.selectByExample(blogArticleTagExample);
        PageInfo<BlogArticleTag> pageInfo = new PageInfo<>(blogArticleTags);
        pageDto.setTotal(pageInfo.getTotal());

        pageDto.setList(blogArticleTags);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param blogArticleTag
     */
    public void save(BlogArticleTag blogArticleTag){

        this.inster(blogArticleTag);

    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        BlogArticleTagExample blogArticleTagExample = new BlogArticleTagExample();
//        blogArticleTagExample.createCriteria().andIdIn(Arrays.asList(ids));
        blogArticleTagMapper.deleteByExample(blogArticleTagExample);
    }

    /**
     * 新增
     * @param blogArticleTag
     */
    private void inster(BlogArticleTag blogArticleTag){
        blogArticleTagMapper.insert(blogArticleTag);
    }



}
