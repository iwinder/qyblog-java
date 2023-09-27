package com.windcoder.qycms.mina.service;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebDto;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.system.dto.UserWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MinaBlogService {
    @Autowired
    private BlogArticleService articleService;

    public void findAllWebDto(BlogArticlePageDto article) {
        article.setType(1);
        articleService.findAllWebDto(article);
    }

    public BlogArticleWebDto findOneArticleWebDto(Long articleId) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setId(articleId);
        articleDto.setPublished(true);
        return articleService.findOneArticleWebDto(articleDto,null,"mina");
    }
    public void addVersion(Long articleId, HttpServletRequest request) {
        articleService.addVersion(articleId,request);
    }
}
