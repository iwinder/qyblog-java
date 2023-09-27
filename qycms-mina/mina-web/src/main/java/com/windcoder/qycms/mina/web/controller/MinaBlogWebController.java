package com.windcoder.qycms.mina.web.controller;

import com.windcoder.qycms.blog.dto.BlogArticleDto;
import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.mina.annotation.MinaVerification;
import com.windcoder.qycms.mina.service.MinaBlogService;
import com.windcoder.qycms.system.annotation.BloomIpLimit;
import com.windcoder.qycms.system.annotation.CurrentUser;
import com.windcoder.qycms.system.annotation.IpApiLimit;
import com.windcoder.qycms.system.annotation.ViewCountLimit;
import com.windcoder.qycms.system.dto.UserWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/web/mina/articles")
public class MinaBlogWebController {
    @Autowired
    private MinaBlogService minaBlogService;

    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    @MinaVerification
    @GetMapping("")
    public ResponseDto allActivities(BlogArticlePageDto article){
        minaBlogService.findAllWebDto(article);
        ResponseDto responseDto = new ResponseDto(article);
        return  responseDto;
    }

    @BloomIpLimit
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    @MinaVerification
    @GetMapping("/{articleId}")
    public ResponseDto get(@PathVariable("articleId") Long articleId, @CurrentUser UserWebDto user) {
        BlogArticleWebDto article = minaBlogService.findOneArticleWebDto(articleId);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }

    @MinaVerification
    @ViewCountLimit
    @PostMapping("/updateViews")
    public ResponseDto updateViews(Long aId, HttpServletRequest request) {
        minaBlogService.addVersion(aId,request);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
