package com.windcoder.qycms.system.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MenusDto {

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

    /**
     * 菜单代理
     */
    private Long targetId;
    private Integer targetType;

    /**
     * 创建者
     */
    private Long createdBy;

    /**
     * 更新者
     */
    private Long lastModifiedBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifiedDate;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getBlanked() {
        return blanked;
    }

    public void setBlanked(Boolean blanked) {
        this.blanked = blanked;
    }

    public List<MenusDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenusDto> children) {
        this.children = children;
    }


    public Integer getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(Integer displayorder) {
        this.displayorder = displayorder;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", blanked=").append(blanked);
        sb.append(", parentId=").append(parentId);
        sb.append(", targetId=").append(targetId);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", lastModifiedBy=").append(lastModifiedBy);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append("]");
        return sb.toString();
    }


}