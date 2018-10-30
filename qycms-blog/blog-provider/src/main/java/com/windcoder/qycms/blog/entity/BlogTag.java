package com.windcoder.qycms.blog.entity;

import org.hibernate.annotations.Formula;

public class BlogTag {
    private Long id;
    private String name;
    @Formula("(select count(*)  from ugc_chapter chapter  where chapter.course_id = id and is_deleted = 0)")
    private Long count;
}
