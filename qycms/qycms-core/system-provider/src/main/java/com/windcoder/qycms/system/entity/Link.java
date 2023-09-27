package com.windcoder.qycms.system.entity;

import java.util.Date;

public class Link extends Auditable {
    private Long id;

    private String name;

    private String url;

    private Boolean showIndex;

    private String description;


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
        sb.append(", createdBy=").append(super.getCreatedBy());
        sb.append(", lastModifiedBy=").append(super.getLastModifiedBy());
        sb.append(", createdDate=").append(super.getCreatedDate());
        sb.append(", lastModifiedDate=").append(super.getLastModifiedDate());
        sb.append("]");
        return sb.toString();
    }
}