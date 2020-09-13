package com.windcoder.qycms.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.basis.utils.PinyinUtilZ;
import com.windcoder.qycms.blog.dto.BlogCategoryWebDto;
import com.windcoder.qycms.blog.entity.BlogCategory;
import com.windcoder.qycms.blog.entity.BlogCategoryExample;
import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.blog.entity.BlogCategoryTree;
import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.repository.mybatis.MyBlogCategoryMapper;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.blog.repository.mybatis.BlogCategoryMapper;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.utils.CopyUtil;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class BlogCategoryService {
    @Resource
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private  BlogCategoryTreeService blogCategoryTreeService;
    private MyBlogCategoryMapper myBlogCategoryMapper;
    private final static String PATH_SEPARATER = ",";
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
    @Transactional
    public void save(BlogCategoryDto blogCategoryDto){
        BlogCategory blogCategory = ModelMapperUtils.map(blogCategoryDto, BlogCategory.class);
        if (null == blogCategory.getId()) {
            Long order = countFirstChildNum(blogCategory.getParentId()) + 1;
            blogCategory.setDisplayOrder(order);
            blogCategory.setDeleted(false);
            if(StringUtils.isBlank(blogCategory.getIdentifier())) {
                blogCategory.setIdentifier(PinyinUtilZ.toHanYuPinyinString(blogCategory.getName()));
            }
            this.inster(blogCategory);
            afterInsert(blogCategory);
        } else {
            // 验证父节点不能更新为自己及自己的子节点
            this.checkRecursive(blogCategory);
            // 更新
            this.update(blogCategory);
            blogCategory = getOne(blogCategory.getId());
            if (blogCategory.getDeleted().equals(false)) {
                afterUpdate(blogCategory);
            }
        }
    }



    public void save(BlogCategory blogCategory){
        if (null == blogCategory.getId()) {
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
        blogCategoryMapper.updateByPrimaryKeySelective(blogCategory);
    }


    public List<BlogCategoryDto> getRoots(BlogCategoryDto dto) {
        BlogCategoryExample example = new BlogCategoryExample();
        BlogCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdIsNull().andDeletedEqualTo(false);
        if (StringUtils.isNotBlank(dto.getName())) {
            criteria.andNameLike(dto.getName());
        }
        example.setOrderByClause("display_order ASC");

        List<BlogCategory> list =  blogCategoryMapper.selectByExample(example);

        return blogCategoryListToDto(list);
    }

    public List<BlogCategoryDto> getChildren(BlogCategoryDto dto) {
        BlogCategoryExample example = new BlogCategoryExample();
        BlogCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(dto.getParentId()).andDeletedEqualTo(false);
        if (StringUtils.isNotBlank(dto.getName())) {
            criteria.andNameLike(dto.getName());
        }
        example.setOrderByClause("display_order ASC");

        List<BlogCategory> list = blogCategoryMapper.selectByExample(example);
        return blogCategoryListToDto(list);
    }

    /**
     * 检查更新分类时不能将上级分类设置为自己或者自己的下级分类
     * @param category
     */
    private void checkRecursive(BlogCategory category) {

        if(category == null) {
            throw new BusinessException("非法的数据请求。");
        }
        if(category.getParentId() != null && category.getId() != null){
            Long id = category.getId();
            Long parentId = category.getParentId();
            if(id.equals(parentId)){
                throw new BusinessException("不能将上级分类设置为自己");
            } else {
                List<BlogCategoryTree> children = blogCategoryTreeService.findAllByParentId(category.getId());
                if(children != null){
                    for (BlogCategoryTree child : children) {
                        if(parentId.equals(child.getChildId())){
                            throw new BusinessException("不能将上级分类设置为自己的下级");
                        }
                    }
                }
            }
        }
    }
    private void afterInsert(BlogCategory category) {
        List<BlogCategoryTree> cxs = new ArrayList<>();
        Long cId = category.getId();
        BlogCategoryTree cx = new BlogCategoryTree(cId, cId, 0, 0L);
        cxs.add(cx);
        Long parentId = category.getParentId();
        if (parentId != null) {
            List<BlogCategoryTree> cxParents = blogCategoryTreeService.findAllByChildId(parentId);
            for (BlogCategoryTree cxParent : cxParents) {
                cx = new BlogCategoryTree(cxParent.getParentId(), cId, cxParent.getDistance() + 1, 0L);
                cxs.add(cx);
            }
            // 所有子分类数+1
            blogCategoryTreeService.updateChildCount(parentId);
        }
        // 更新关系
        blogCategoryTreeService.save(cxs);
        updatePath(category);
    }
    private void afterUpdate(BlogCategory category) {
        Long cId = category.getId();

        BlogCategoryTree oldParent = blogCategoryTreeService.findByChildIdAndDistanceEquals(cId, 1);
        if(oldParent != null) {
            Long oldParentId = oldParent.getParentId();
            blogCategoryTreeService.cleanParents(cId);
            blogCategoryTreeService.minusChildCount(oldParentId);
        }

        Long parentId = category.getParentId();
        if (parentId != null) {
            List<BlogCategoryTree> cxs = new ArrayList<>();
            BlogCategoryTree cx;
            List<BlogCategoryTree> cxParents = blogCategoryTreeService.findAllByChildId(parentId);
            List<BlogCategoryTree> cxChildren = blogCategoryTreeService.findAllByParentId(cId);
            for (BlogCategoryTree cxParent : cxParents) {
                for (BlogCategoryTree cxChild : cxChildren) {
                    cx = new BlogCategoryTree(cxParent.getParentId(), cxChild.getChildId(),
                            cxParent.getDistance() + cxChild.getDistance() + 1, cxChild.getChildCount());
                    cxs.add(cx);
                }
            }
            blogCategoryTreeService.updateChildCount(parentId);
            blogCategoryTreeService.save(cxs);
        }
        if (hasModefiedName(category) || hasModefiedParent(category)) {
            updatePath(category);
        }
    }

    @Transactional
    public void updatePath(BlogCategory category) {
        Long parenId = category.getParentId();
        if(parenId != null) {
            BlogCategory parent = getOne(parenId);
            category.setIdPath(parent.getIdPath() + PATH_SEPARATER + category.getId());
            category.setNamePath(parent.getNamePath() + PATH_SEPARATER + category.getName());
        } else {
            category.setIdPath(category.getId()+"");
            category.setNamePath(category.getName());
        }
        // 更新路径
        this.save(category);
        // 更新子分类路径
        updateChildrenPath(category);


    }

    /**
     * 递归更新子分类的路径
     * @param category
     */
    private void updateChildrenPath(BlogCategory category) {
        List<BlogCategory> children = getChildren(category.getId());
        if (!children.isEmpty()) {
            myBlogCategoryMapper.updatePathChildren(category.getId(), category.getIdPath() + PATH_SEPARATER, category.getNamePath() + PATH_SEPARATER);
            for (BlogCategory child: children) {
                updateChildrenPath(child);
            }
        }
    }

    /**
     * 根据 id 查询子分类
     * @param parentId
     * @return
     */
    private List<BlogCategory> getChildren(Long parentId) {
        BlogCategoryExample categoryExample = new BlogCategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(parentId);
        return blogCategoryMapper.selectByExample(categoryExample);
    }

    public BlogCategory getOne(Long id) {
        return blogCategoryMapper.selectByPrimaryKey(id);
    }
    public Long countFirstChildNum(Long parentId) {
        BlogCategoryExample blogCategoryExample = new BlogCategoryExample();
        BlogCategoryExample.Criteria criteria = blogCategoryExample.createCriteria();
        if(null == parentId ) {
            criteria.andParentIdIsNull();
        } else {
            criteria.andParentIdEqualTo(parentId);
        }
        return blogCategoryMapper.countByExample(blogCategoryExample);
    }

    private boolean hasModefiedName(BlogCategory category) {
        String namePath = category.getNamePath();
        if (StringUtils.isBlank(namePath)) {
            return true;
        }

        String[] namePathArr = namePath.split(PATH_SEPARATER);
        if (namePathArr == null || namePathArr.length == 0) {
            return true;
        }

        String oldName = namePathArr[namePathArr.length - 1];
        return !StringUtils.equals(oldName, category.getName());
    }

    private boolean hasModefiedParent(BlogCategory category) {
        String idPath = category.getIdPath();
        if (StringUtils.isBlank(idPath)) {
            return true;
        }

        String[] idPathArr = idPath.split(PATH_SEPARATER);
        if (idPathArr == null || idPathArr.length == 0) {
            return true;
        }

        if((idPathArr.length == 1 && category.getParentId() != null)
                || (idPathArr.length > 1 && category.getParentId() == null)) {
            return true;
        }

        if(idPathArr.length == 1 && category.getParentId() == null) {
            return false;
        }

        String oldParentId = idPathArr[idPathArr.length - 2];
        if (!NumberUtils.isParsable(oldParentId)) {
            return true;
        }
        return NumberUtils.toLong(oldParentId) != category.getParentId().longValue();
    }

    private List<BlogCategoryDto> blogCategoryListToDto(List<BlogCategory> list) {
        Type type = new TypeToken<List<BlogCategoryDto>>() {}.getType();
        List<BlogCategoryDto> articlesDto = ModelMapperUtils.map(list,type);
        Long count = 0L;
        for (BlogCategoryDto articleDto: articlesDto) {
            count = blogCategoryTreeService.findChildrenCount(articleDto.getId());
            articleDto.setChildrenCount(count);
            if (count.longValue()==0) {
                articleDto.setIsLeaf(true);
            }
        }
        return articlesDto;
    }


    public BlogCategoryDto findOneCategoryDto(Long categoryId) {
        BlogCategoryExample categoryExample  = new BlogCategoryExample();
        categoryExample.createCriteria()
                .andDeletedEqualTo(false).andIdEqualTo(categoryId);
        List<BlogCategory> blogCategorys= blogCategoryMapper.selectByExample(categoryExample);
        if (blogCategorys.isEmpty()) {
            throw new BusinessException("非法的数据请求");
        }
        BlogCategory blogCategory = blogCategorys.get(0);
        BlogCategoryDto blogCategoryDto = ModelMapperUtils.map(blogCategory, BlogCategoryDto.class);
        if(blogCategory.getParentId()!=null) {
          BlogCategory parent =  blogCategoryMapper.selectByPrimaryKey(blogCategory.getParentId());
          blogCategoryDto.setParent(ModelMapperUtils.map(parent, BlogCategoryDto.class));
        }
        return blogCategoryDto;
    }

    public BlogCategory findByIdentifier(String identifier) {
        BlogCategoryExample example = new BlogCategoryExample();
        example.createCriteria().andIdentifierEqualTo(identifier);
        List<BlogCategory> blogCategories = blogCategoryMapper.selectByExample(example);
        if(!blogCategories.isEmpty()) {
            return blogCategories.get(0);
        }
        return null;
    }


    public BlogCategoryWebDto findByIdForWeb(Long id) {
        BlogCategory category = getOne(id);
        if (category == null) {
            return new BlogCategoryWebDto();
        }
        return ModelMapperUtils.map(category, BlogCategoryWebDto.class);
    }

    public BlogCategoryWebDto findByIdentifierForWeb(String identifier) {
        BlogCategory category = findByIdentifier(identifier);
        if (category == null) {
            return new BlogCategoryWebDto();
        }
        return ModelMapperUtils.map(category, BlogCategoryWebDto.class);
    }
}
