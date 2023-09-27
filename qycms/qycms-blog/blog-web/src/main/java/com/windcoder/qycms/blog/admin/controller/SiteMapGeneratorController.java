package com.windcoder.qycms.blog.admin.controller;

import com.windcoder.qycms.blog.service.SiteMapGeneratorService;
import com.windcoder.qycms.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/siteMap")
@Slf4j
public class SiteMapGeneratorController {
    @Autowired
    private SiteMapGeneratorService generatorService;
    @GetMapping("")
    public ResponseDto generatorXml() {
        ResponseDto responseDto = new ResponseDto();
        generatorService.generatorXml();
        return responseDto;
    }
}
