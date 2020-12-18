package com.windcoder.qycms.blog.jobs;

import com.windcoder.qycms.blog.service.SiteMapGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SiteMapGeneratorTask {
    @Autowired
    private SiteMapGeneratorService generatorService;
    //每天0点执行一次
    @Scheduled(cron = "0 0 0 * * ? ")
    public void generatorXml() {
        log.info("开始生成网站地图");
        generatorService.generatorXml();
        log.info("生成网站地图结束");
    }

}
