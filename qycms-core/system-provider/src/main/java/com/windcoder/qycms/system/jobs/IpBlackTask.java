package com.windcoder.qycms.system.jobs;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import com.windcoder.qycms.system.service.SysIpBlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class IpBlackTask {
    @Autowired
    private SysIpBlackService sysIpBlackService;

    //每天23点执行一次
    @Scheduled(cron = "0 0 0/6 * * ? ")
    public void createBloom() {
        log.info("开始初始化黑名单布隆缓存过滤");
        sysIpBlackService.updateBlackFromRedis();
        log.info("初始化黑名单布隆缓存过滤结束");
    }
}
