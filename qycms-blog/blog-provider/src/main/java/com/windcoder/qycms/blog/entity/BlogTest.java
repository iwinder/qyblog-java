package com.windcoder.qycms.blog.entity;

import javax.persistence.*;

@Entity
@Table(name="blog_test", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "viewCount"}),
        @UniqueConstraint(columnNames = {"code", "viewCount"})
})
public class BlogTest {

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
