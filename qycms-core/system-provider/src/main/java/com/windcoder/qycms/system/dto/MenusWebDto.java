package com.windcoder.qycms.system.dto;

import java.util.List;

public class MenusWebDto {
    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单地址
     */
    private String url;

    /**
     * 是否新窗口打开：0否， 1是
     */
    private Boolean blanked;

    /**
     * 排序
     */
    private Integer displayorder;

    /**
     * 父级菜单
     */
    private Long parentId;

    private List<MenusDto> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getBlanked() {
        return blanked;
    }

    public void setBlanked(Boolean blanked) {
        this.blanked = blanked;
    }

    public Integer getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(Integer displayorder) {
        this.displayorder = displayorder;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<MenusDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenusDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenusWebDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", blanked=" + blanked +
                ", displayorder=" + displayorder +
                ", parentId=" + parentId +
                ", children=" + children +
                '}';
    }
}
