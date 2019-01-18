package com.windcoder.qycms.blog.web.controller;


import com.windcoder.qycms.blog.dto.BlogArticleBaseDto;
import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.entity.BlogArticle;
import com.windcoder.qycms.blog.service.BlogArticleService;
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
@RequestMapping("api/web/articles")
public class BlogArticleWebController {

    @Autowired
    private BlogArticleService articleService;

    @ModelAttribute(name = "articleForUpdate")
    public BlogArticle getArticle(@RequestParam(name = "id", required = false) Long id) {
        if (null != id) {
            BlogArticle article = articleService.findOne(id);
            article.setCategory(null);
            return article;
        } else {
            return new BlogArticle();
        }
    }

    @PutMapping("")
    public BlogArticleDto add(@RequestBody() BlogArticle article) {
        article = articleService.save(article);

        return ModelMapperUtils.map(article, BlogArticleDto.class);
    }

    @PostMapping("/{articleId}")
    public BlogArticleDto update(@PathVariable("articleId") Long articleId, @ModelAttribute(name = "articleForUpdate") BlogArticle article) {
        article = articleService.update(article);

        return ModelMapperUtils.map(article, BlogArticleDto.class);
    }

    @GetMapping("/{articleId}")
    public BlogArticleDto get(@PathVariable("articleId") Long articleId) {
        BlogArticle article = articleService.findOne(articleId);

        return ModelMapperUtils.map(article, BlogArticleDto.class);
    }

    @GetMapping("")
    public Page<BlogArticleBaseDto> allActivities(BlogArticle article,
                                                  @RequestParam(name= "searchText", required=false)String searchText,
                                                  @PageableDefault(direction= Sort.Direction.DESC,sort={"lastModifiedDate"}) Pageable pageable){
        if(StringUtils.isNotBlank(searchText)) {
            article.setTitle(searchText);
        }
        article.setIsPublished(true);
        Page<BlogArticle> articles = articleService.findAll(article,pageable);
        Type type = new TypeToken<List<BlogArticleBaseDto>>() {}.getType();
        List<BlogArticleBaseDto> articlesDto = ModelMapperUtils.map(articles.getContent(),type);
        return  new PageImpl<>(articlesDto,pageable,articles.getTotalElements());
    }
}
