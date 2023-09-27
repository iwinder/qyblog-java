package com.windcoder.qycms.quartz.dto;

import com.windcoder.qycms.dto.PageDto;
import lombok.Data;
import org.quartz.JobDataMap;

import java.util.Date;



public class QuartzBeanPageDto extends PageDto {
    /** 任务id */
    private String id;

    /** 任务名称 */
    private String jobName;

    /** 任务描述 */
    private String description;

    /** 任务组 */
    private String jobGroup;

    /** 任务执行类 */
    private String jobClass;

    /** 任务状态 启动还是暂停*/
    private Integer status;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务循环间隔-单位：分钟
     */
    private Integer interval;

    /**
     * 任务结束时间
     */
    private Date endTime;

    /** 任务运行时间表达式 */
    private String cronExpression; 

    private long startTimeLong;
    private long endTimeLong;
    private Boolean cronFlag = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        if(cronExpression!=null) {
            setCronFlag(true);
        }
    }

    public long getStartTimeLong() {
        return startTimeLong;
    }

    public void setStartTimeLong(long startTimeLong) {
        this.startTimeLong = startTimeLong;
        if(startTimeLong>0) {
            setStartTime(new Date(startTimeLong));
        }
    }

    public long getEndTimeLong() {
        return endTimeLong;
    }

    public void setEndTimeLong(long endTimeLong) {
        this.endTimeLong = endTimeLong;
        if(endTimeLong>0) {
            setEndTime(new Date(endTimeLong));
        }
    }

    public Boolean getCronFlag() {
        return cronFlag;
    }

    public void setCronFlag(Boolean cronFlag) {
        this.cronFlag = cronFlag;

    }
}
