package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.blog.entity.BlogCategory;
import com.windcoder.qycms.blog.entity.BlogCategoryExample;
import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogCategoryMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class BlogCategoryService {
    @Resource
    private BlogCategoryMapper blogCategoryMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        BlogCategoryExample blogCategoryExample = new BlogCategoryExample();
        List<BlogCategory> blogCategorys = blogCategoryMapper.selectByExample(blogCategoryExample);
        PageInfo<BlogCategory> pageInfo = new PageInfo<>(blogCategorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<BlogCategoryDto> blogCategoryDtoList = CopyUtil.copyList(blogCategorys, BlogCategoryDto.class);
        pageDto.setList(blogCategoryDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param blogCategoryDto
     */
    public void save(BlogCategoryDto blogCategoryDto){
        BlogCategory blogCategory = CopyUtil.copy(blogCategoryDto, BlogCategory.class);
        if (StringUtils.isEmpty(blogCategory.getId())) {
            this.inster(blogCategory);
        } else {
            this.update(blogCategory);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        BlogCategoryExample blogCategoryExample = new BlogCategoryExample();
        blogCategoryExample.createCriteria().andIdIn(Arrays.asList(ids));
        blogCategoryMapper.deleteByExample(blogCategoryExample);
    }

    /**
     * 新增
     * @param blogCategory
     */
    private void inster(BlogCategory blogCategory){
        Date now = new Date();
        blogCategory.setCreatedDate(now);
        blogCategory.setLastModifiedDate(now);
        blogCategoryMapper.insert(blogCategory);
    }

    /**
     * 更新
     * @param blogCategory
     */
    private void update(BlogCategory blogCategory){
        blogCategory.setLastModifiedDate(new Date());
        blogCategoryMapper.updateByPrimaryKey(blogCategory);
    }

}
