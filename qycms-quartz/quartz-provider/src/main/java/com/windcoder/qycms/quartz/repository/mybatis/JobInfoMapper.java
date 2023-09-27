package com.windcoder.qycms.quartz.repository.mybatis;

import com.windcoder.qycms.quartz.dto.QuartzBeanDto;
import com.windcoder.qycms.quartz.dto.QuartzBeanPageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobInfoMapper {
    List<QuartzBeanDto> findJobList(@Param("pageDto") QuartzBeanPageDto pageDto);
    QuartzBeanDto findOne(@Param("jobDto") QuartzBeanDto quartzBeanDto);
}
