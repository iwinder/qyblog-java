package com.windcoder.qycms.system.entity;

import java.util.Set;

/**
 * Description: 角色
 * User: WindCoder
 * Date: 2018-04-15
 * Time: 18:32 下午
 */

public class Role extends Auditable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Set<Privilege> privileges;
    private Long displayOrder;
    /**
     * 备注，用于简单描述等
     */
    private String remark;

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

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
