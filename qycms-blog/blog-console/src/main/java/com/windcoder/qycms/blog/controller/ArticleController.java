package com.windcoder.qycms.blog.controller;

import com.windcoder.qycms.blog.dto.ArticleBaseDto;
import com.windcoder.qycms.blog.dto.ArticleDto;
import com.windcoder.qycms.blog.entity.Article;
import com.windcoder.qycms.blog.service.ArticleService;
import com.windcoder.qycms.core.system.dto.UserDto;
import com.windcoder.qycms.core.system.entity.User;
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
@RequestMapping("api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ModelAttribute(name = "articleForUpdate")
    public Article getArticle(@RequestParam(name = "id", required = false) Long id) {
        if (null != id) {
            Article article = articleService.findOne(id);
            return article;
        } else {
            return new Article();
        }
    }

    @PutMapping("")
    public ArticleDto add(@RequestBody() Article article) {
        article = articleService.save(article);

        return ModelMapperUtils.map(article, ArticleDto.class);
    }

    @PostMapping("/{articleId}")
    public ArticleDto update(@PathVariable("articleId") Long articleId, @ModelAttribute(name = "articleForUpdate") Article article) {
        article = articleService.update(article);

        return ModelMapperUtils.map(article, ArticleDto.class);
    }

    @GetMapping("/{articleId}")
    public ArticleDto get(@PathVariable("articleId") Long articleId) {
        Article article = articleService.findOne(articleId);

        return ModelMapperUtils.map(article, ArticleDto.class);
    }

    @GetMapping("")
    public Page<ArticleBaseDto> allActivities(Article article,
                                                  @RequestParam(name= "searchText", required=false)String searchText,
                                                  @PageableDefault(direction= Sort.Direction.DESC,sort={"lastModifiedDate"}) Pageable pageable){
        if(StringUtils.isNotBlank(searchText)) {
            article.setTitle(searchText);
        }
        Page<Article> users = articleService.findAll(article,pageable);
        Type type = new TypeToken<List<ArticleBaseDto>>() {}.getType();
        List<ArticleBaseDto> articleDtos = ModelMapperUtils.map(users.getContent(),type);
        return  new PageImpl<>(articleDtos,pageable,users.getTotalElements());
    }
}
