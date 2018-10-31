package com.windcoder.qycms.blog.entity;

import com.windcoder.qycms.core.system.entity.Auditable;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="blog_tag")
public class BlogTag extends Auditable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Formula("(select count(*)  from blog_article_tag bat  where bat.tag_id = id)")
    private Long count;

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
