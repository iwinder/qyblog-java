package com.windcoder.qycms.core.system.service;

import com.windcoder.qycms.core.system.entity.Category;
import com.windcoder.qycms.core.system.repository.CategoryRepository;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService extends BaseService<Category,Long, CategoryRepository> {
    private final static String PATH_SEPARATER = ",";

    /**
     * 获取根节点
     * @param searchText
     * @param pageable
     * @return
     */
    public List<Category> getRoots(String searchText, Pageable pageable) {
        return repository.findAll((root,query,cb)->{
            Predicate predicate = cb.isNull(root.get("parent"));
//            predicate = cb.and(predicate, cb.equal(root.get("isDeleted"), false));
            if (StringUtils.isNotBlank(searchText)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")),
                        "%" + StringUtils.trim(searchText).toLowerCase() + "%"));
            }
            return predicate;
        },pageable.getSort());
    }

    /**
     * 获取子节点
     * @param parentId
     * @param searchText
     * @param pageable
     * @return
     */
    public List<Category> getChildren(Long parentId,String searchText, Pageable pageable) {
        return repository.findAll((root,query,cb)->{
            Predicate predicate = cb.equal(root.get("parent").get("id"), parentId);
//            predicate = cb.and(predicate, cb.equal(root.get("isDeleted"), false));
            if (StringUtils.isNotBlank(searchText)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")),
                        "%" + StringUtils.trim(searchText).toLowerCase() + "%"));
            }
            return predicate;
        },pageable.getSort());
    }

    /**
     * 保存分类
     * @param category
     * @return
     */
    public Category saveCategory(Category category) {
        if (category.getId() == null ) {
            Long nowMax = Long.valueOf(0);
            if(category.getParent()!=null && category.getParent().getId() !=null ) {
                nowMax = getMaxDisplayOrder(category.getParent().getId());
            }else {
                nowMax = getMaxDisplayOrder(null);
            }
            category.setDisplayOrder(nowMax+1);
        }

        this.save(category);
        updatePath(category);
        return category;
    }

    /**
     * 上移
     * @param parentId
     * @param categoryId
     * @param searchText
     * @param pageable
     */
    public void moveUp(Long parentId,Long categoryId,String searchText, Pageable pageable) {
        List<Category> categories = null;
        if(parentId == null) {
            categories = getRoots(searchText,pageable);
        }else {
            categories = getChildren(parentId, searchText,pageable);
        }
        if(categories == null || categories.size() == 0) {
            return;
        }
        Category sourceCourse = null;
        Category targetCourse = null;
        int listSize = categories.size();
        for(int index=0;index<listSize;index++) {
            sourceCourse = categories.get(index);
            if(!categoryId.equals(sourceCourse.getId())) {
                continue;
            }
            if(index == 0) {
                throw new BusinessException("当前信息已处于最前位置");
            }else {
                targetCourse = categories.get(index-1);
            }
            break;
        }
        changeDisplayOrder(sourceCourse,targetCourse);
    }

    /**
     * 下移
     * @param parentId
     * @param categoryId
     * @param searchText
     * @param pageable
     */
    public void moveDown(Long parentId, Long categoryId,String searchText ,Pageable pageable) {
        List<Category> categories = null;
        if(parentId == null) {
            categories = getRoots(searchText,pageable);
        }else {
            categories = getChildren(parentId, searchText,pageable);
        }
        if(categories == null || categories.size() == 0) {
            return;
        }
        Category sourceCourse = null;
        Category targetCourse = null;
        int listSize = categories.size();
        for(int index=0;index<listSize;index++) {
            sourceCourse = categories.get(index);
            if(!categoryId.equals(sourceCourse.getId())) {
                continue;
            }
            if(index == listSize-1) {
                throw new BusinessException("当前信息已处于最后位置");
            }else {
                targetCourse = categories.get(index+1);
            }
            break;
        }
        changeDisplayOrder(sourceCourse,targetCourse);
    }

    /**
     * 查询分类列表-用于学员端，无分页查询
     * @param category
     * @return
     */
    public List<Category>  findAllForLearner(Category category) {
        return repository.findAll(getPublicSpecification(category), new Sort(Direction.DESC, "displayOrder"));
    }

    /**
     * 更新分类 IdPath和titlePath
     * @param category
     */
    @Transactional
    public void updatePath(Category category) {
        Category parent = category.getParent();
        if(parent != null) {
            if(parent.getIdPath() == null) {
                parent = findOne(parent.getId());
            }
            category.setIdPath(parent.getIdPath() + PATH_SEPARATER + category.getId());
            category.setTitlePath(parent.getTitlePath() + PATH_SEPARATER + category.getTitle());
        }else {
            category.setIdPath(category.getId()+"");
            category.setTitlePath(category.getTitle());
        }
        super.save(category);
        updateChildrenPath(category);
    }
    /**
     * 更新分类子节点 IdPath和titlePath
     * @param category
     */
    public void updateChildrenPath(Category category) {

        List<Category> children = getChildren(category);
        if(!children.isEmpty()) {
            repository.updatePathChildren(category.getId(), category.getIdPath() + PATH_SEPARATER, category.getTitlePath() + PATH_SEPARATER);
            for (Category child : children) {
                updateChildrenPath(child);
            }
        }
    }


    /**
     * 获取子分类
     * @param category
     * @return
     */
    private List<Category> getChildren(Category category) {
        Category sample = new Category();
        sample.setParent(category);
        return repository.findAll(getPublicSpecification(sample));
    }

    /**
     * 交换分类排序
     * @param sourceCategory
     * @param targetCategory
     */
    private void changeDisplayOrder(Category sourceCategory,Category targetCategory) {
        if (sourceCategory == null || targetCategory ==null) {
            throw new BusinessException("未找到所选分类或主题");
        }
        Long displayOrder= sourceCategory.getDisplayOrder().longValue();
        sourceCategory.setDisplayOrder(targetCategory.getDisplayOrder());
        targetCategory.setDisplayOrder(displayOrder);
        this.save(sourceCategory);
        this.save(targetCategory);
    }

    /**
     * 获取当前最大排序
     * @param
     * @param parentId
     * @return
     */
    public Long getMaxDisplayOrder(Long parentId) {
        Long nowMax = null;
        if(parentId == null) {
            nowMax = repository.getMaxDisplayOrderByParentIdIsNull();
        }else {
            nowMax =  repository.getMaxDisplayOrderByParentId(parentId);
        }
        if(nowMax == null){
            nowMax = 0L;
        }
        return nowMax;
    }

    public Specification<Category> getPublicSpecification(Category category){
        return new Specification<Category>(){

            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                    Predicate predicate =  cb.equal(root.get("isDeleted"), false);
                    Predicate predicate = null;
                    if(category.getParent() != null && category.getParent().getId() != null){
                        predicate =  cb.equal(root.get("parent").get("id"),  category.getParent().getId());
                    }


                    if (StringUtils.isNotBlank(category.getTitle())) {
                        if( predicate != null ){
                            predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")),
                                    "%" + StringUtils.trim(category.getTitle()).toLowerCase() + "%"));
                        }else{
                            predicate = cb.like(cb.lower(root.get("title")),
                                    "%" + StringUtils.trim(category.getTitle()).toLowerCase() + "%");
                        }

                    }
                    return predicate;
            }
        };
    }

    public void delete(Long[] ids) {
        for (Long id: ids){
            repository.delete(id);
        }
    }
}
