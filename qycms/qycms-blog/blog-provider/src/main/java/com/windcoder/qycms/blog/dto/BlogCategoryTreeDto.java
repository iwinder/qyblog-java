package com.windcoder.qycms.blog.dto;


public class BlogCategoryTreeDto {

    /**
     * 父级分类id
     */
    private Long parentId;

    /**
     * 子级分类id
     */
    private Long childId;

    /**
     * 层级: 处于第几级分类
     */
    private Integer distance;

    /**
     * 子分类数
     */
    private Long childcount;

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

    public Long getChildcount() {
        return childcount;
    }

    public void setChildcount(Long childcount) {
        this.childcount = childcount;
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
        sb.append(", childcount=").append(childcount);
        sb.append("]");
        return sb.toString();
    }


}