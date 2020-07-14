package com.windcoder.qycms.blog.admin.controller;


import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.blog.service.BlogArticleService;
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
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/admin/articles")
public class BlogArticleController {

    @Autowired
    private BlogArticleService articleService;

//    @ModelAttribute(name = "articleForUpdate")
//    public BlogArticle getArticle(@RequestParam(name = "id", required = false) Long id) {
//        if (null != id) {
//            BlogArticle article = articleService.findOne(id);
//            article.setCategory(null);
//            return article;
//        } else {
//            return new BlogArticle();
//        }
//    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody BlogArticleDto article) {
        articleService.save(article);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

//    @PostMapping("/{articleId}")
//    public ResponseDto update(@PathVariable("articleId") Long articleId, @ModelAttribute(name = "articleForUpdate") BlogArticle article) {
//        article = articleService.update(article);
//
//        return ModelMapperUtils.map(article, BlogArticleDto.class);
//    }

    @GetMapping("/{articleId}")
    public ResponseDto get(@PathVariable("articleId") Long articleId) {
        BlogArticleDto article = articleService.findOneArticleDto(articleId);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article){

        articleService.findAll(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }

    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids){
        articleService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
