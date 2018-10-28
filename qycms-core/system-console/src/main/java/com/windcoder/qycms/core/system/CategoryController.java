package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.system.dto.CategoryBaseDto;
import com.windcoder.qycms.core.system.dto.CategoryDto;
import com.windcoder.qycms.core.system.entity.Category;
import com.windcoder.qycms.core.system.service.CategoryService;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("CategoryForUpdate")
    public Category CategoryForUpdate(@RequestParam(name = "id", required = false) Long id) {
        if (null != id) {
            Category ugcCategory =  categoryService.findOne(id);
            if(ugcCategory == null){
                throw new BusinessException("400");
            }
            ugcCategory.setParent(null);
            return ugcCategory;
        }
        return new Category();
    }

    /**
     * 查询分类列表
     * @param parentId
     * @param searchText
     * @param pageable
     * @return
     */
    @GetMapping("")
    public List<CategoryDto> findUgcClassifications(@RequestParam(name= "parentId", required=false)Long parentId,
                                                               @RequestParam(name= "searchText", required=false)String searchText,
                                                               @PageableDefault(direction=Direction.DESC,sort={"displayOrder"}) Pageable pageable){
        if(parentId==null) {
            List<Category> categories = categoryService.getRoots(searchText,pageable);
            Type type = new TypeToken<List<CategoryDto>>(){}.getType();
            return ModelMapperUtils.map(categories,type);
        }else {
            List<Category> categories = categoryService.getChildren(parentId, searchText,pageable);
            Type type = new TypeToken<List<CategoryDto>>(){}.getType();
            return ModelMapperUtils.map(categories,type);
        }
    }

    @GetMapping("tree")
    public List<CategoryBaseDto> findCategoryTree(){
        Category category = new Category();
        List<Category> categories = categoryService.findCategoryTree(category);
        Type type = new TypeToken<List<CategoryBaseDto>>(){}.getType();
        return ModelMapperUtils.map(categories,type);
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CategoryDto getOne(@PathVariable("id") Long id) {
        Category ugcClassification = categoryService.findOne(id);
        return ModelMapperUtils.map(ugcClassification, CategoryDto.class);
    }

    /**
     * 创建分类
     * @param ugcCategory
     * @return
     */
    @PutMapping("")
    public CategoryDto create(@RequestBody Category ugcCategory) {
        ugcCategory = categoryService.saveCategory(ugcCategory);
        return ModelMapperUtils.map(ugcCategory, CategoryDto.class);
    }

    /**
     * 更新分类
     * @param id
     * @param ugcCategory
     * @return
     */
    @PostMapping("/{id}")
    public CategoryDto update(@PathVariable("id") Long id,
                                         @ModelAttribute("CategoryForUpdate") Category ugcCategory) {
        ugcCategory = categoryService.saveCategory(ugcCategory);
        return ModelMapperUtils.map(ugcCategory, CategoryDto.class);
    }

    /**
     * 删除分类
     * @param ids
     */
    @DeleteMapping("")
    public void delete(@RequestParam("ids")Long[] ids){
        categoryService.delete(ids);
    }

    /**
     * 上移
     * @param searchText
     * @param parentId
     * @param id
     * @param pageable
     */
    @PostMapping("/{id}/up")
    public void moveUp(@RequestParam(name= "searchText", required=false) String searchText,
                       @RequestParam(name= "parentId", required=false) Long parentId,
                       @PathVariable("id") Long id,
                       @PageableDefault(direction=Direction.DESC,sort={"displayOrder"}) Pageable pageable) {
        categoryService.moveUp(parentId, id,searchText, pageable);
    }

    /**
     * 下移
     * @param searchText
     * @param parentId
     * @param id
     * @param pageable
     */
    @PostMapping("/{id}/down")
    public void moveDown(@RequestParam(name= "searchText", required=false) String searchText,
                         @RequestParam(name= "parentId", required=false)Long parentId,
                         @PathVariable("id") Long id,
                         @PageableDefault(direction=Direction.DESC,sort={"displayOrder"}) Pageable pageable) {
        categoryService.moveDown(parentId, id,searchText, pageable);
    }

}
