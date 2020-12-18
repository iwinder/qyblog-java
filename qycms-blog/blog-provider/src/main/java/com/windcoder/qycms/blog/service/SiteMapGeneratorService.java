package com.windcoder.qycms.blog.service;

import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogArticleWebBaseDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.entity.GlobalProperties;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.service.SiteConfigService;
import com.windcoder.qycms.utils.Constants;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SiteMapGeneratorService {
    @Autowired
    private SiteConfigService siteConfigService;
    @Autowired
    private BlogArticleService blogArticleService;
    @Autowired
    private GlobalProperties globalProperties;

    public void generatorXml(){
        String siteUrl = siteConfigService.findValueOneByKey("siteInfo:base","site_url");

        // 获取文章列表生成sitemap
        generatorPostXml(siteUrl, 1);
        // 获取页面列表生成sitemap
        generatorPostXml(siteUrl, 2);
        // 生成sitemap索引页面
        String parentPath = globalProperties.getXmlPath();
        File parentDir = new File(parentPath);
        if (!parentDir.exists()){
            parentDir.mkdirs();
        }
        try {
            SitemapGenerator.of(siteUrl)
                    .addPage(WebPage.builder().name(Constants.SITE_MAP_FILE_POST_NAME).lastMod(new Date()).build())
                    .addPage(WebPage.builder().name(Constants.SITE_MAP_FILE_PAGE_NAME).lastMod(new Date()).build())
                    .toFile(parentDir,Constants.SITE_MAP_FILE_INDEX_NAME);
        } catch (IOException e) {
            log.error("生成网站地图异常",e);
            throw new BusinessException("生成文章网站地图异常");
        }

    }

    public void generatorPostXml(String siteUrl, int type) {
        BlogArticlePageDto pageDto = new BlogArticlePageDto();
        // 分页拼接文章xml
        int page = 1;
        pageDto.setType(type);
        pageDto.setPage(page);
        pageDto.setSize(100);
        SitemapGenerator sitemapGenerator = SitemapGenerator.of(siteUrl);
        if (type==1) {
            String indexUrl = siteUrl;
            if (!indexUrl.endsWith("/")){
                indexUrl = indexUrl+"/" ;
            }
            sitemapGenerator.addPage(WebPage.builder().name(indexUrl).lastMod(new Date()).build());
        }
        blogArticleService.finAllPublishedPermaLink(pageDto);
        List<BlogArticleWebBaseDto> list = pageDto.getList();
        for (BlogArticleWebBaseDto dto: list) {
            sitemapGenerator.addPage(WebPage.builder().name(dto.getPermaLink()).lastMod(dto.getPublishedDate()).build());
        }
        int totalPage = pageDto.getPages();
        for (page =2;page<=totalPage;page++) {
            pageDto.setPage(page);
            blogArticleService.finAllPublishedPermaLink(pageDto);
            list = pageDto.getList();
            for (BlogArticleWebBaseDto dto: list) {
                sitemapGenerator.addPage(WebPage.builder().name(dto.getPermaLink()).lastMod(dto.getPublishedDate()).build());
            }
        }
        String parentPath = globalProperties.getXmlPath();
        File parentDir = new File(parentPath);
        if (!parentDir.exists()){
            parentDir.mkdirs();
        }
        String targetFileName = type==1?Constants.SITE_MAP_FILE_POST_NAME:Constants.SITE_MAP_FILE_PAGE_NAME;
        try {
            sitemapGenerator.toFile(parentDir,targetFileName);
        } catch (IOException e) {
            log.error("生成文章/页面网站地图异常",e);
            throw new BusinessException("生成文章/页面网站地图异常");
        }
    }
}
