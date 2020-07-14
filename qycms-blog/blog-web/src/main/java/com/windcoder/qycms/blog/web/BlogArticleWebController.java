package com.windcoder.qycms.blog.web;

import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/articles")
public class BlogArticleWebController {

    @Autowired
    private BlogArticleService articleService;
    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article){

        articleService.findAll(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }
}
