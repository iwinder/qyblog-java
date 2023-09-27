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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //编号
    private String name; //资源名称
    private String url; //资源路径
    private String type;//类型
    private String description;//界面UI显示字段
    private String identifier; //权限字符串
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Privilege parent;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
    private Set<Privilege> children = new HashSet<Privilege>();

    private Boolean isHide;//是否隐藏
//    private String parentIds; //父编号列表

    private Boolean isAvailable; //是否可用,如果不可用将不会添加给用户


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Privilege getParent() {
        return parent;
    }

    public void setParent(Privilege parent) {
        this.parent = parent;
    }

    public Set<Privilege> getChildren() {
        return children;
    }

    public void setChildren(Set<Privilege> children) {
        this.children = children;
    }

    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        this.isAvailable = available;
    }
}
