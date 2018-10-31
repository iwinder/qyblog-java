package com.windcoder.qycms.blog.repository;


import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.repository.SupportRepository;

public interface BlogTagRepository extends SupportRepository<BlogTag,Long> {

    BlogTag findByName(String name);
}
