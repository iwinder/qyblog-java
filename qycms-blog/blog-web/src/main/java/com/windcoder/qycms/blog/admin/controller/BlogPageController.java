package com.windcoder.qycms.blog.admin.controller;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.annotation.CurrentUser;
import com.windcoder.qycms.system.dto.UserWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/pages")
public class BlogPageController {
    @Autowired
    private BlogArticleService articleService;

    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article){
        article.setType(2);
        articleService.findAll(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody BlogArticleDto article, @CurrentUser UserWebDto user) {
        articleService.save(article,user);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

    @GetMapping("/{articleId}")
    public ResponseDto get(@PathVariable("articleId") Long articleId) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setId(articleId);
        BlogArticleDto article = articleService.findOneArticleDto(articleDto);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids){
        articleService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
