package com.windcoder.qycms.core.system.entity;



import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/** 系统用户
 * Description:
 * User: WindCoder
 * Date: 2018-04-05
 * Time: 23:16 下午
 */


@Data
public class User extends Auditable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String avatar;
    private String nickname;
    /**
     * 帐号状态:0正常,1禁用
     */
    private Boolean disable;
    private Boolean deleted;

    public User(Long id) {
        this.id = id;
    }
    public User() {
    }


    public String getCredentialsSalt() {
        return username + salt;
    }
}
