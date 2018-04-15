package com.windcoder.qycms.core.system.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Description: 角色
 * User: WindCoder
 * Date: 2018-04-15
 * Time: 18:32 下午
 */
@Entity
@Table(name = "sys_role")
public class Role extends Auditable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_privilege", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges;
    private Long displayOrder;
    /**
     * 备注，用于简单描述等
     */
    private String remark;
}
