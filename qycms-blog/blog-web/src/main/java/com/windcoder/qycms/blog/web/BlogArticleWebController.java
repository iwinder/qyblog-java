package com.windcoder.qycms.blog.web;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebDto;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/articles")
public class BlogArticleWebController {

    @Autowired
    private BlogArticleService articleService;


    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article){
        article.setType(1);
        articleService.findAllWebDto(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }



    @GetMapping("/{articleId}")
    public ResponseDto get(@PathVariable("articleId") Long articleId) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setId(articleId);
        articleDto.setPublished(true);
        BlogArticleWebDto article = articleService.findOneArticleWebDto(articleDto);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

    @GetMapping("/name/{articleName}")
    public ResponseDto getByName(@PathVariable("articleName") String articleName) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setPermaLink(articleName);
        articleDto.setPublished(true);
        BlogArticleWebDto article = articleService.findOneArticleWebDto(articleDto);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }




}
