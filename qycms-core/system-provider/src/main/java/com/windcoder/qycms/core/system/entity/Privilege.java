package com.windcoder.qycms.core.system.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Description: 权限
 * User: WindCoder
 * Date: 2018-04-15
 * Time: 18:36 下午
 */
@Entity
@Table(name="sys_privilege")
public class Privilege extends Auditable{
    private Long id; //编号
    private String name; //资源名称
    private String url; //资源路径
    private String type;//类型
    private String description;//界面UI显示字段
    private String permission; //权限字符串
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Privilege parent;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
    private Set<Privilege> children = new HashSet<Privilege>();

    private Boolean ishide;//是否隐藏
//    private String parentIds; //父编号列表

    private Boolean isAvailable; //是否可用,如果不可用将不会添加给用户
}
