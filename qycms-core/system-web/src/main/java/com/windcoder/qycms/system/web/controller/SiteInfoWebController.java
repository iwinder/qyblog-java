package com.windcoder.qycms.system.web.controller;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.SiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/web/siteInfo/")
@Slf4j
public class SiteInfoWebController {
    @Resource
    private SiteConfigService siteConfigService;


    @GetMapping(value = "base")
    public ResponseDto findBase() {
       JSONObject jsonObject =  siteConfigService.findInfoObj(1);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "content")
    public ResponseDto findcCntent() {
        JSONObject jsonObject =  siteConfigService.findInfoObj(2);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "social")
    public ResponseDto findSocial() {
        JSONObject jsonObject =  siteConfigService.findInfoObj(3);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "other")
    public ResponseDto findOther() {
        JSONObject jsonObject =  siteConfigService.findInfoObj(4);
        if (jsonObject.getString("site_pay_flag").equals("false")) {
            jsonObject.remove("site_weixin_pay_qr");
            jsonObject.remove("site_alipay_pay_qr");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }
}
