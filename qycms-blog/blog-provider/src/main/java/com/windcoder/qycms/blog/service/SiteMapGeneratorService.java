package com.windcoder.qycms.blog.service;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.SiteConfigService;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SiteMapGeneratorService {
    @Autowired
    private SiteConfigService siteConfigService;
    public void s(ResponseDto responseDto ){
        String siteUrl = siteConfigService.findValueOneByKey("siteInfo:base","site_url");
        WebPage page  = WebPage.of("ideapeizhiresin");
        page.setLastMod(new Date());
        // 获取文章列表生成sitemap
        // 获取页面列表生成sitemap
        // 生成sitemap索引页面
        String sitemap = SitemapGenerator.of(siteUrl)
                .addPage("20201209shengming")
                .addPage(page)
                .addPage(WebPage.builder().name("barww").lastMod(new Date()).build())
                .toString();
        responseDto.setContent(sitemap);

    }
}
