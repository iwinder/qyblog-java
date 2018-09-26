package com.windcoder.qycms.blog.entity;

import javax.persistence.Lob;

public class Article {
    private Long id;
    private String title;
    @Lob
    private String content;

    private Boolean isPublished;
}
