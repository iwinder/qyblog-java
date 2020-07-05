package com.windcoder.qycms.system.entity;



/**
 * Description: 授权
 * User: WindCoder
 * Date: 2018-04-15
 * Time: 18:36 下午
 */

public class Permission extends Auditable {

    private Long id;

    private User user;

    private Role role;

    private Privilege privilege;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
}
