package com.windcoder.qycms.blog.jobs;

import com.windcoder.qycms.blog.service.BlogArticleService;
import com.windcoder.qycms.system.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class PostViewCountTask  extends QuartzJobBean {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BlogArticleService articleService;

//    @Scheduled(cron = "0 30 23 * * ?")
//    @Scheduled(cron = "0 0/10 * * * ?")
//    public void updatePostView() {
//        log.info("计数落库开始......");
//        Set<String> keys = redisUtil.getKeys(redisUtil.POST_VIEW_COUNT + "*");
//
//        keys.forEach(e-> {
//            Long  viewCount = redisUtil.getPostViewCount(e);
//            String[] strings = e.split(":");
//            articleService.updateView(Long.valueOf(strings[2]),viewCount);
//            redisUtil.delPostViewCount(e);
//        });
//        log.info("计数落库结束......");
//    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("计数落库开始......");
        Set<String> keys = redisUtil.getKeys(redisUtil.POST_VIEW_COUNT + "*");

        keys.forEach(e-> {
            Long  viewCount = redisUtil.getPostViewCount(e);
            String[] strings = e.split(":");
            articleService.updateView(Long.valueOf(strings[2]),viewCount);
            redisUtil.delPostViewCount(e);
        });
        log.info("计数落库结束......");
    }
}
