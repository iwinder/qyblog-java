package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.blog.entity.BlogCategoryTree;
import com.windcoder.qycms.blog.entity.BlogCategoryTreeExample;
import com.windcoder.qycms.blog.dto.BlogCategoryTreeDto;
import com.windcoder.qycms.blog.repository.mybatis.MyBlogCategoryTreeMapper;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogCategoryTreeMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class BlogCategoryTreeService {
    @Resource
    private BlogCategoryTreeMapper blogCategoryTreeMapper;
    @Autowired
    private MyBlogCategoryTreeMapper myBlogCategoryTreeMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        BlogCategoryTreeExample blogCategoryTreeExample = new BlogCategoryTreeExample();
        List<BlogCategoryTree> blogCategoryTrees = blogCategoryTreeMapper.selectByExample(blogCategoryTreeExample);
        PageInfo<BlogCategoryTree> pageInfo = new PageInfo<>(blogCategoryTrees);
        pageDto.setTotal(pageInfo.getTotal());
        List<BlogCategoryTreeDto> blogCategoryTreeDtoList = CopyUtil.copyList(blogCategoryTrees, BlogCategoryTreeDto.class);
        pageDto.setList(blogCategoryTreeDtoList);
    }


    /**
     * 存在：更新，不存在：新增
     * @param cxs
     */
    public void save(List<BlogCategoryTree> cxs) {
        myBlogCategoryTreeMapper.inserOrUpdateBatch(cxs);
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        BlogCategoryTreeExample blogCategoryTreeExample = new BlogCategoryTreeExample();
//        blogCategoryTreeExample.createCriteria().andIdIn(Arrays.asList(ids));
        blogCategoryTreeMapper.deleteByExample(blogCategoryTreeExample);
    }





    /**
     * 根据Id查询子分类id的所有父级分类
     * @param childId
     * @return
     */
    public List<BlogCategoryTree> findAllByChildId(Long childId) {
        BlogCategoryTreeExample blogCategoryTreeExample = new BlogCategoryTreeExample();
        blogCategoryTreeExample.createCriteria().andChildIdEqualTo(childId);
        return blogCategoryTreeMapper.selectByExample(blogCategoryTreeExample);
    }

    /**
     * 根据Id查询父分类id的所有子级分类
     * @param parentId
     * @return
     */
    public List<BlogCategoryTree> findAllByParentId(Long parentId) {
        BlogCategoryTreeExample blogCategoryTreeExample = new BlogCategoryTreeExample();
        blogCategoryTreeExample.createCriteria().andParentIdEqualTo(parentId);
        return blogCategoryTreeMapper.selectByExample(blogCategoryTreeExample);
    }

    /**
     * 根据子分类，将所有父级分类到子分类数 +1
     * @param childId
     */
    public void updateChildCount(Long childId) {
        myBlogCategoryTreeMapper.updateChildCount(childId);
    }


    public BlogCategoryTree findByChildIdAndDistanceEquals(Long childId, Integer distance) {
        BlogCategoryTreeExample blogCategoryTreeExample = new BlogCategoryTreeExample();
        blogCategoryTreeExample.createCriteria().andChildIdEqualTo(childId).andDistanceEqualTo(distance);
        return blogCategoryTreeMapper.selectByExample(blogCategoryTreeExample).get(0);

    }


    public void cleanParents(Long id) {
        myBlogCategoryTreeMapper.cleanParents(id);
    }

    public void minusChildCount(Long childId) {
        myBlogCategoryTreeMapper.minusChildCount(childId);
    }

    public Long findChildrenCount(Long childId) {
        return myBlogCategoryTreeMapper.selectChildrenCount(childId);
    }
}
