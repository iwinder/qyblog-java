package com.windcoder.qycms.core.system.entity;

import javax.persistence.*;

/**
 * Description: 授权
 * User: WindCoder
 * Date: 2018-04-15
 * Time: 18:36 下午
 */
@Entity
@Table(name="sys_privilege")
public class Permission extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;
}
