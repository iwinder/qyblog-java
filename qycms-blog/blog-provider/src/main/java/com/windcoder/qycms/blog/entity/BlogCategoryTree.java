package com.windcoder.qycms.blog.entity;

public class BlogCategoryTree {
    private Long parentId;

    private Long childId;

    private Integer distance;

    private Long childCount;

    public BlogCategoryTree(){}
    public BlogCategoryTree(Long parentId, Long childId, Integer distance, Long childCount) {
        this.parentId = parentId;
        this.childId = childId;
        this.distance = distance;
        this.childCount = childCount;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Long getChildCount() {
        return childCount;
    }

    public void setChildCount(Long childCount) {
        this.childCount = childCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", parentId=").append(parentId);
        sb.append(", childId=").append(childId);
        sb.append(", distance=").append(distance);
        sb.append(", childCount=").append(childCount);
        sb.append("]");
        return sb.toString();
    }
}