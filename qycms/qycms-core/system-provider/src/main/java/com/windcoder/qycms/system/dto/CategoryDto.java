package com.windcoder.qycms.system.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private Long id;
    private String title;
    private Long parentId;
    private CategoryDto parent;
    private String idPath;
    private String titlePath;
    private String key;
    private Long displayOrder;
    private Long childrenCount;
    private boolean hasChildren;
    private List<CategoryDto> children;
    private String description;
    private String keyWord;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
        this.key = String.valueOf(id);
    }

    public void setTitle(String title) {
        this.title = title;
    }





    public Long getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
    public Long getChildrenCount() {
        return childrenCount;
    }
    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = (childrenCount != null ? childrenCount : 0);
        this.hasChildren = this.childrenCount > 0;
    }
    public boolean getHasChildren() {
        return hasChildren || ( this.childrenCount!=null && this.childrenCount > 0);
    }
    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
    public List<CategoryDto> getChildren() {
        return children;
    }
    public void setChildren(List<CategoryDto> children) {
        this.children = children;
        if(this.children == null){
            this.children = new ArrayList<CategoryDto>();
        }
    }
    public String getIdPath() {
        return idPath;
    }
    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }
    public String getTitlePath() {
        return titlePath;
    }
    public void setTitlePath(String namePath) {
        this.titlePath = titlePath;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public CategoryDto getParent() {
        return parent;
    }
    public void setParent(CategoryDto parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
