package com.windcoder.qycms.system.entity;

import java.util.Date;

public class MenusAgent extends Auditable {
    private Long id;

    private String name;

    private Integer type;



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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", createdBy=").append(super.getCreatedBy());
        sb.append(", lastModifiedBy=").append(super.getLastModifiedBy());
        sb.append(", createdDate=").append(super.getCreatedDate());
        sb.append(", lastModifiedDate=").append(super.getLastModifiedDate());
        sb.append("]");
        return sb.toString();
    }
}