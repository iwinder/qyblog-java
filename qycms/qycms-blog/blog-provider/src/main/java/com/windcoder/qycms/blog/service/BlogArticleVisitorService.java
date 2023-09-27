package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.blog.entity.BlogArticleVisitor;
import com.windcoder.qycms.blog.entity.BlogArticleVisitorExample;
import com.windcoder.qycms.blog.dto.BlogArticleVisitorDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogArticleVisitorMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class BlogArticleVisitorService {
    @Resource
    private BlogArticleVisitorMapper blogArticleVisitorMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        BlogArticleVisitorExample blogArticleVisitorExample = new BlogArticleVisitorExample();
        List<BlogArticleVisitor> blogArticleVisitors = blogArticleVisitorMapper.selectByExample(blogArticleVisitorExample);
        PageInfo<BlogArticleVisitor> pageInfo = new PageInfo<>(blogArticleVisitors);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<BlogArticleVisitorDto>>() {}.getType();
        List<BlogArticleVisitorDto> blogArticleVisitorDtoList = ModelMapperUtils.map(blogArticleVisitors, type);
        pageDto.setList(blogArticleVisitorDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param blogArticleVisitorDto
     */
    public void save(BlogArticleVisitorDto blogArticleVisitorDto){
        BlogArticleVisitor blogArticleVisitor = ModelMapperUtils.map(blogArticleVisitorDto, BlogArticleVisitor.class);
        save(blogArticleVisitor);
    }

    public void save(BlogArticleVisitor blogArticleVisitor) {
        if (null == blogArticleVisitor.getId()) {
            this.inster(blogArticleVisitor);
        } else {
            this.update(blogArticleVisitor);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        BlogArticleVisitorExample blogArticleVisitorExample = new BlogArticleVisitorExample();
        blogArticleVisitorExample.createCriteria().andIdIn(Arrays.asList(ids));
        blogArticleVisitorMapper.deleteByExample(blogArticleVisitorExample);
    }

    /**
     * 新增
     * @param blogArticleVisitor
     */
    private void inster(BlogArticleVisitor blogArticleVisitor){
        Date now = new Date();
        blogArticleVisitor.setCreatedDate(now);
        blogArticleVisitor.setLastModifiedDate(now);
        blogArticleVisitorMapper.insertSelective(blogArticleVisitor);
    }

    /**
     * 更新
     * @param blogArticleVisitor
     */
    private void update(BlogArticleVisitor blogArticleVisitor){
        blogArticleVisitor.setLastModifiedDate(new Date());
        blogArticleVisitorMapper.updateByPrimaryKeySelective(blogArticleVisitor);
    }

}
