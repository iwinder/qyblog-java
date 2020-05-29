package com.windcoder.qycms.blog.entity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name="blog_tests" ,  uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "viewCount"}),
        @UniqueConstraint(columnNames = {"code", "viewCount"})
})
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
public class BlogTests {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Long viewCount;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
