package com.windcoder.qycms.blog.web;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebDto;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.annotation.*;
import com.windcoder.qycms.system.dto.UserWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/web/articles")
public class BlogArticleWebController {

    @Autowired
    private BlogArticleService articleService;

    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article, @CurrentUser UserWebDto user){
        article.setType(1);
        if (user!=null && user.getId()!=null && user.getId()>0) {
            article.setUserId(user.getId());
        }
        articleService.findAllWebDto(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }

    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    @GetMapping("/{articleId}")
    public ResponseDto get(@PathVariable("articleId") Long articleId, @CurrentUser UserWebDto user) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setId(articleId);
        articleDto.setPublished(true);
        BlogArticleWebDto article = articleService.findOneArticleWebDto(articleDto,user);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }
    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    @BloomLimit
    @GetMapping("/name/{articleName}")
    public ResponseDto getByName(@PathVariable("articleName") String articleName, @CurrentUser UserWebDto user) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setPermaLink(articleName);
        articleDto.setPublished(true);
        BlogArticleWebDto article = articleService.findOneArticleWebDto(articleDto,user);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

//    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    @ViewCountLimit
    @PostMapping("/updateViews")
    public ResponseDto updateViews(Long articleId, HttpServletRequest request) {
        articleService.addVersion(articleId,request);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }








}
