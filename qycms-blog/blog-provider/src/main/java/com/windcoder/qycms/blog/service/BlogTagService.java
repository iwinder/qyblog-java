package com.windcoder.qycms.blog.service;

import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.repository.jpa.BlogTagRepository;
import com.windcoder.qycms.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class BlogTagService extends BaseService<BlogTag,Long, BlogTagRepository> {

    public Page<BlogTag> findAll(BlogTag tag, Pageable pageable) {
        return super.findAll((root, query,  cb) -> {
            Predicate predicate = null;
            if(tag.getName() != null) {
                predicate = cb.like( cb.lower(root.get("title")),
                        "%"+ StringUtils.trim(tag.getName()).toLowerCase()+"%" );
            }

            return predicate;

        },pageable);
    }

    BlogTag findByName(String name){
        return repository.findByName(name);
    }
}
