package com.windcoder.qycms.quartz.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.quartz.dto.QuartzBeanDto;
import com.windcoder.qycms.quartz.dto.QuartzBeanPageDto;
import com.windcoder.qycms.quartz.entity.QuartzBean;
import com.windcoder.qycms.quartz.repository.mybatis.JobInfoMapper;
import com.windcoder.qycms.system.dto.UserWebDto;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class JobInfoService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JobInfoMapper jobInfoMapper;

    public void createScheduleJobSimple(QuartzBean quartzBean) throws Exception {
        // 获取定时任务执行类
        //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
        Class<? extends Job> jobClass =   (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
            String group = StringUtils.isNoneBlank(quartzBean.getJobGroup())?quartzBean.getJobGroup():null;
            // 构建定时任务信息
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                    .withIdentity(quartzBean.getJobName(), group);
            if(quartzBean.getJobDataMap()!=null) {
                jobBuilder.setJobData(quartzBean.getJobDataMap());
            }
            JobDetail jobDetail = jobBuilder.build();
            // 设置定时任务执行方式
            SimpleScheduleBuilder simpleScheduleBuilder = null;
            if(quartzBean.getInterval() == null) {
                simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            } else {
                simpleScheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever(quartzBean.getInterval());
            }
            // 构建触发器 trigger
            Trigger trigger = null;
            if (quartzBean.getInterval() == null || quartzBean.getInterval().intValue() == 0) {
                // 单次执行
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(quartzBean.getJobName(),group)
                        .withSchedule(simpleScheduleBuilder)
                        .startAt(quartzBean.getStartTime())
                        .withDescription(quartzBean.getDescription())
                        .build();
            } else {
                // 循环执行
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(quartzBean.getJobName(), group)
                        .withSchedule(simpleScheduleBuilder)
                        .startAt(quartzBean.getStartTime())
                        .endAt(quartzBean.getEndTime())
                        .withDescription(quartzBean.getDescription())
                        .build();
        }
        scheduler.scheduleJob(jobDetail, trigger);

    }

    /**
     * 创建
     * @param quartzBean
     */
    public void createScheduleJobCron(QuartzBean quartzBean)  throws Exception {
        // 获取到定时任务的执行类 必须是类的绝对路径名称
        //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
        Class<? extends Job> jobClass =  (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                    .withIdentity(quartzBean.getJobName());
            if(quartzBean.getJobDataMap()!=null) {
                jobBuilder.setJobData(quartzBean.getJobDataMap());
            }
            JobDetail jobDetail = jobBuilder.build();
            // 设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            // 构建触发器 trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzBean.getJobName(),quartzBean.getJobGroup())
                    .withDescription(quartzBean.getDescription())
                    .withSchedule(scheduleBuilder).build();

        scheduler.scheduleJob(jobDetail, trigger);

    }

    /**
     * 根据任务名称暂停定时任务
     * @param jobName
     * @param jobGroup
     */
    public void pauseScheduleJob(String jobName,String jobGroup)  throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName,StringUtils.isNoneBlank(jobGroup)?jobGroup:null);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 根据任务名称恢复定时任务
     * @param jobName
     * @param jobGroup 任务组（没有分组传值null）
     */
    public void resumeScheduleJob(String jobName, String jobGroup)  throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName,StringUtils.isNoneBlank(jobGroup)?jobGroup:null);
        scheduler.resumeJob(jobKey);
    }

    /**
     * 根据任务名称立即运行一次定时任务
     * @param jobName
     * @param jobGroup
     */
    public void runOne(String jobName, String jobGroup)  throws Exception {
            JobKey jobKey = JobKey.jobKey(jobName,StringUtils.isNoneBlank(jobGroup)?jobGroup:null);
            scheduler.triggerJob(jobKey);

    }

    /**
     * 更新定时任务Simple
     * @param quartzBean
     */
    public void updateScheduleJobSimple(QuartzBean quartzBean)  throws Exception {
        String group = StringUtils.isNoneBlank(quartzBean.getJobGroup())?quartzBean.getJobGroup():null;
        // 获取对应任务的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName(),group);
        // 设置定时任务执行方式
        SimpleScheduleBuilder simpleScheduleBuilder = null;
        if (quartzBean.getInterval() == null  || quartzBean.getInterval().intValue() == 0) {
            // 单次
            simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        } else {
            // 循环
            simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(quartzBean.getInterval());
        }
        // 构建触发器trigger
        Trigger trigger = null;
        if (quartzBean.getInterval() == null) {
            // 单次
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzBean.getJobName(), group)
                    .withSchedule(simpleScheduleBuilder)
                    .withDescription(quartzBean.getDescription())
                    .startAt(quartzBean.getStartTime())
                    .build();
        } else {
            // 循环
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzBean.getJobName(), group)
                    .withSchedule(simpleScheduleBuilder)
                    .startAt(quartzBean.getStartTime())
                    .withDescription(quartzBean.getDescription())
                    .endAt(quartzBean.getEndTime())
                    .build();
        }
        // 重置对应的Job
        scheduler.rescheduleJob(triggerKey, trigger);


    }

    /**
     * 更新定时任务Cron
      * @param quartzBean
     */
    public void updateScheduleJobCron(QuartzBean quartzBean)  throws Exception {
        // 获取到对应任务的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName());
        // 设置定时任务执行方式
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
        // 重新构建任务的触发器 trigger

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(scheduleBuilder)
                .withDescription(quartzBean.getDescription())
                .build();
        // 重置对应的Job
        scheduler.rescheduleJob(triggerKey,trigger);

    }

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     * @param jobName
     * @param jobGroup
     */
    public void deleteSchedulJob(String jobName, String jobGroup)  throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName,StringUtils.isNoneBlank(jobGroup)?jobGroup:null);
        scheduler.deleteJob(jobKey);

    }

    /**
     * 获取任务状态
     * @param jobName
     * @param jobGroup
     * @return
     *
     * ("BLOCKED", "阻塞");
     * ("COMPLETE", "完成");
     * ("ERROR", "出错");
     *("NONE", "不存在");
     * ("NORMAL", "正常");
     * ("PAUSED", "暂停");
     */
    public String getScheduleJobStatus(String jobName, String jobGroup)  throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,StringUtils.isNoneBlank(jobGroup)?jobGroup:null);
        Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
        return state.name();
    }

    /**
     * 根据任务名称判断任务是否存在
     * @param jobName
     * @param jobGroup
     * @return
     */
    public Boolean checkExistsScheduleJob(String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName,StringUtils.isNoneBlank(jobGroup)?jobGroup:null);
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据任务组删除定时任务
     * @param jobGroup
     * @return
     */
    public Boolean deleteGroupJob(String jobGroup)  throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
            Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
            List<JobKey> jobKeyList =  new ArrayList<JobKey>();
            jobKeyList.addAll(jobKeySet);
            return scheduler.deleteJobs(jobKeyList);
    }

    /**
     * 根据任务组批量删除定时任务
     * @param jobKeyList
     * @return
     */
    public Boolean batchDeleteGroupJob(List<JobKey> jobKeyList) {
        try {
            return scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void batchQueryGroupJob(List<JobKey> jobKeyList, String jobGroup) {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        Set<JobKey> jobKeySet = null;
        try {
            jobKeySet = scheduler.getJobKeys(matcher);
            jobKeyList.addAll(jobKeySet);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void findAll(QuartzBeanPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        List<QuartzBeanDto> jobs = jobInfoMapper.findJobList(pageDto);
        PageInfo<QuartzBeanDto> pageInfo = new PageInfo<>(jobs);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(jobs);
    }

    public QuartzBeanDto  findOneJobDto(QuartzBeanDto quartzBeanDto) {
        return jobInfoMapper.findOne(quartzBeanDto);
    }

    /**
     *  新增
     * @param jobDto
     * @param user
     */
    public void save(QuartzBeanDto jobDto, UserWebDto user)  throws Exception {
        QuartzBean jobInfo = ModelMapperUtils.map(jobDto, QuartzBean.class);
        JobDataMap map = new JobDataMap();
        map.put("userId", user.getId());
        jobInfo.setJobDataMap(map);
        if(StringUtils.isBlank(jobInfo.getCronExpression())) {
            createScheduleJobSimple(jobInfo);
        } else {
            createScheduleJobCron(jobInfo);
        }
    }

    public void update(QuartzBeanDto jobDto, UserWebDto user)  throws Exception {
        QuartzBean jobInfo = ModelMapperUtils.map(jobDto, QuartzBean.class);
        if(StringUtils.isBlank(jobInfo.getCronExpression())) {
            updateScheduleJobSimple(jobInfo);
        } else {
            updateScheduleJobCron(jobInfo);
        }
    }


    public void deleteOne(QuartzBeanDto job) throws Exception {
        deleteSchedulJob(job.getJobName(),job.getJobGroup());
    }
}
