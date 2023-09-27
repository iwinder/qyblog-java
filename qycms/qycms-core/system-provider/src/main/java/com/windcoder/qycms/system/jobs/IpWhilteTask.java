package com.windcoder.qycms.system.jobs;


import com.windcoder.qycms.system.service.SiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 更新Ip白名单过滤器
 */
@Component
@Slf4j
public class IpWhilteTask {
    @Resource
    private SiteConfigService siteConfigService;


    //每天23点执行一次
    @Scheduled(cron = "0 0 0/3 * * ?")
    public void createBloom() {
        log.info("开始初始化白名单本地缓存过滤");
        siteConfigService.refreshIpWhilteList();
        log.info("初始化白名单本地缓存过滤结束");
    }
}
