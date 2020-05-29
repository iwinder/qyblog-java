package com.windcoder.qycms.blog.entity;

import com.windcoder.qycms.core.system.entity.User;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
@Entity
@Table(name="blog_testss", uniqueConstraints = {@UniqueConstraint(columnNames = {"author_id","title"}),
        @UniqueConstraint(columnNames = {"author_id","code"})})
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
public class BlogTestss {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Long viewCount;
    private String code;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
