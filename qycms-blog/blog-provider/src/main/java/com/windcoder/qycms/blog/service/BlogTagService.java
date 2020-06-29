package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.entity.BlogTagExample;
import com.windcoder.qycms.blog.dto.BlogTagDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogTagMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class BlogTagService {
    @Resource
    private BlogTagMapper blogTagMapper;

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
     * @param blogTagDto
     */
    public void save(BlogTagDto blogTagDto){
        BlogTag blogTag = CopyUtil.copy(blogTagDto, BlogTag.class);
        if (StringUtils.isEmpty(blogTag.getId())) {
            this.inster(blogTag);
        } else {
            this.update(blogTag);
        }
    }

    /**
     * 删除
     * @param id
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
        blogTagMapper.insert(blogTag);
    }

    /**
     * 更新
     * @param blogTag
     */
    private void update(BlogTag blogTag){
        blogTag.setLastModifiedDate(new Date());
        blogTagMapper.updateByPrimaryKey(blogTag);
    }

}
