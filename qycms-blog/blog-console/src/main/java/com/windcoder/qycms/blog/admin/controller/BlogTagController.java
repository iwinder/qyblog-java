package com.windcoder.qycms.blog.admin.controller;

import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogTagDto;
import com.windcoder.qycms.blog.dto.BlogTagPageDto;
import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.blog.service.BlogTagService;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("api/adminn/tags")
public class BlogTagController {

    @Autowired
    private BlogTagService tagService;

    @GetMapping("")
    public ResponseDto allActivities(BlogTagPageDto tag){

        tagService.findAll(tag);
        ResponseDto responseDto = new ResponseDto(tag);
        return  responseDto;
    }
}
