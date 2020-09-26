package com.windcoder.qycms.blog.web;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebDto;
import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.annotation.BloomLimit;
import com.windcoder.qycms.system.annotation.ServiceLimit;
import com.windcoder.qycms.system.annotation.ViewCountLimit;
import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/web/articles")
public class BlogArticleWebController {

    @Autowired
    private BlogArticleService articleService;

    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article){
        article.setType(1);
        articleService.findAllWebDto(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }


    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    @GetMapping("/{articleId}")
    public ResponseDto get(@PathVariable("articleId") Long articleId) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setId(articleId);
        articleDto.setPublished(true);
        BlogArticleWebDto article = articleService.findOneArticleWebDto(articleDto);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }
    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    @BloomLimit
    @GetMapping("/name/{articleName}")
    public ResponseDto getByName(@PathVariable("articleName") String articleName) {
        BlogArticleDto articleDto = new BlogArticleDto();
        articleDto.setPermaLink(articleName);
        articleDto.setPublished(true);
        BlogArticleWebDto article = articleService.findOneArticleWebDto(articleDto);
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
