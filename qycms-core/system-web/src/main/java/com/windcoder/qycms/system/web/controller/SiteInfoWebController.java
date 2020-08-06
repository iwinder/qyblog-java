package com.windcoder.qycms.system.web.controller;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.SiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/web/siteInfo/")
@Slf4j
public class SiteInfoWebController {
    @Resource
    private SiteConfigService siteConfigService;


    @GetMapping(value = "base")
    public ResponseDto findBase() {
        Map<Object, Object> jsonObject =  siteConfigService.findInfoObjString(1);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "content")
    public ResponseDto findcCntent() {
        Map<Object, Object> jsonObject =  siteConfigService.findInfoObjString(2);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "social")
    public ResponseDto findSocial() {
        Map<Object, Object> jsonObject =  siteConfigService.findInfoObjString(3);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "other")
    public ResponseDto findOther() {
        Map<Object, Object> jsonObject=  siteConfigService.findInfoObjString(4);
        if (jsonObject.get("site_pay_flag").equals("false")) {
            jsonObject.remove("site_weixin_pay_qr");
            jsonObject.remove("site_alipay_pay_qr");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject.toString());
        return responseDto;
    }
}
