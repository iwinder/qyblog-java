package com.windcoder.qycms.blog.jobs;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.windcoder.qycms.system.filters.BloomCacheFilter;
import com.windcoder.qycms.system.repository.mybatis.MyCommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 更新文章布隆过滤器
 */
@Component
@Slf4j
public class BlogLinkBloomTask  {
    @Autowired
    private MyCommonMapper myCommonMapper;
    //每天23点执行一次
    @Scheduled(cron = "0 0 23 * * ?")
    public void createBloom() {
        log.info("开始初始化布隆缓存过滤");
        List<String> list = myCommonMapper.findAllBlogPostLink();
        BloomCacheFilter.refresh(list);
        log.info("初始化布隆缓存过滤结束");
    }

//    @Override
//    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        log.info("开始初始化布隆缓存过滤");
//        List<String> list = myCommonMapper.findAllBlogPostLink();
//        BloomCacheFilter.refresh(list);
//        log.info("初始化布隆缓存过滤结束");
//    }
}
