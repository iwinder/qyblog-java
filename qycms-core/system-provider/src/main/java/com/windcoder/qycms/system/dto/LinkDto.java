package com.windcoder.qycms.system.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LinkDto {

    /**
     * id
     */
    private Long id;

    /**
     * 链接名称
     */
    private String name;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 是否首页外链：0不是， 1是
     */
    private Boolean showIndex;

    /**
     * 描述
     */
    private String description;

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

    public Boolean getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Boolean showIndex) {
        this.showIndex = showIndex;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        sb.append(", showIndex=").append(showIndex);
        sb.append(", description=").append(description);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", lastModifiedBy=").append(lastModifiedBy);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append("]");
        return sb.toString();
    }


}