package com.windcoder.qycms.core.system.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sys_category")
@DynamicInsert
public class Category extends Auditable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;

    private String keyWord;


    /**
     * 父级分类
     */
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;


    /**
     * 排序
     */
    private Long displayOrder;

    /**
     * 子分类总数
     */
    @Formula("(select count(*) from sys_category sc "
            + "where sc.parent_id = id)")
    private Long childrenCount;


    @Transient
    private List<Category> parents;
    @Transient
    private List<Category> children;

    private String type;

    /**
     * id路径
     */
    @Column(length = 1000)
    private String idPath;

    /**
     * title(名称)路径
     */
    @Column(length = 1000)
    private String titlePath;

    @Transient
    private String key;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.key = String.valueOf(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKey() {
        return key;
    }


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
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
        this.childrenCount = childrenCount;
    }

    public List<Category> getParents() {
        return parents;
    }

    public void setParents(List<Category> parents) {
        this.parents = parents;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setTitlePath(String titlePath) {
        this.titlePath = titlePath;
    }

    public String getDescription() {
        return description;
    }

    public void setKey(String key) {
        if(StringUtils.isBlank(key)){
            this.key = String.valueOf(id);
        }else {
            this.key = key;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
