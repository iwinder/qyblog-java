package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.windcoder.qycms.blog.dto.BlogTagPageDto;
import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.blog.entity.BlogArticleExample;
import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.entity.BlogTagExample;
import com.windcoder.qycms.blog.repository.mybatis.BlogArticleMapper;
import com.windcoder.qycms.blog.repository.mybatis.BlogTagMapper;
import com.windcoder.qycms.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void findAll(BlogTagPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        BlogTagExample tagExample = new BlogTagExample();
        tagExample.createCriteria().andNameLike(pageDto.getSearchText());

        List<BlogTag> tags = blogTagMapper.selectByExample(tagExample);
        PageInfo<BlogTag> pageInfo = new PageInfo<>(tags);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(tags);
    }

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    BlogTag findByName(String name){
        BlogTagExample tagExample = new BlogTagExample();
        tagExample.createCriteria().andNameEqualTo(name);
        return blogTagMapper.selectByExample(tagExample).get(0);
    }
}
