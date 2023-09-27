package com.windcoder.qycms.quartz.admin.controller;


import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.quartz.dto.QuartzBeanDto;
import com.windcoder.qycms.quartz.dto.QuartzBeanPageDto;
import com.windcoder.qycms.quartz.service.JobInfoService;
import com.windcoder.qycms.system.annotation.CurrentUser;
import com.windcoder.qycms.system.dto.UserWebDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/admin/jobInfo")
@Slf4j
public class JobInfoController {
    @Resource
    private JobInfoService jobInfoService;

    public static final String BUSINESS_NAME = "评论表";
    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(QuartzBeanPageDto pageDto) {
        jobInfoService.findAll(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }


    @GetMapping("/one")
    public ResponseDto findOne(QuartzBeanDto jobDto) {
        QuartzBeanDto oneJobDto = jobInfoService.findOneJobDto(jobDto);
        ResponseDto responseDto = new ResponseDto(oneJobDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody QuartzBeanDto job, @CurrentUser UserWebDto user) {
        ResponseDto responseDto = null;
        try {
            jobInfoService.save(job, user);
            responseDto = new ResponseDto(job);
        } catch (Exception e) {
            responseDto = new ResponseDto(job);
            responseDto.setSuccess(false);
            e.printStackTrace();
        }

        return responseDto;
    }

    @PostMapping("/update")
    public ResponseDto update(@RequestBody QuartzBeanDto job, @CurrentUser UserWebDto user) {
        ResponseDto responseDto = null;
        try {
            jobInfoService.update(job, user);
            responseDto = new ResponseDto(job);
        } catch (Exception e) {
            responseDto = new ResponseDto(job);
            responseDto.setSuccess(false);
            e.printStackTrace();
        }

        return responseDto;
    }


    @DeleteMapping("/deleteOne")
    public ResponseDto deleteOne(@RequestBody QuartzBeanDto job) {
        ResponseDto responseDto = null;
        try {
            jobInfoService.deleteOne(job);
            responseDto = new ResponseDto(job);
        } catch (Exception e) {
            responseDto = new ResponseDto(job);
            responseDto.setSuccess(false);
            e.printStackTrace();
        }

        return responseDto;
    }
}
