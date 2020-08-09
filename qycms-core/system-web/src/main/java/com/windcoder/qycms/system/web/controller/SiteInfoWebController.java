package com.windcoder.qycms.system.web.controller;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.dto.MenusWebDto;
import com.windcoder.qycms.system.service.MenusService;
import com.windcoder.qycms.system.service.SiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/web/siteInfo/")
@Slf4j
public class SiteInfoWebController {
    @Resource
    private SiteConfigService siteConfigService;
    @Autowired
    private MenusService menusService;


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
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "all")
    public ResponseDto findAll() {
        Map<Object, Object> jsonObject=  siteConfigService.findInfoObjString(0);
        if (jsonObject.get("site_pay_flag").equals("false")) {
            jsonObject.remove("site_weixin_pay_qr");
            jsonObject.remove("site_alipay_pay_qr");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(jsonObject);
        return responseDto;
    }

    @GetMapping(value = "menus")
    public ResponseDto findAllMenus() {
        Map<String, List<Object>> nowAllMenus = menusService.findNowAllMenus();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(nowAllMenus);
        return responseDto;
    }
}
