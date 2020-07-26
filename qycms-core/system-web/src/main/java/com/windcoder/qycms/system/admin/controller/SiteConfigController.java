package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.SiteConfig;
import com.windcoder.qycms.system.dto.SiteConfigDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.SiteConfigService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/admin/siteInfo")
@Slf4j
public class SiteConfigController {

    @Resource
    private SiteConfigService siteConfigService;

    public static final String BUSINESS_NAME = "站点配置表";

    /**
     * 列表查询
     * @param
     * @return
     */
    @GetMapping("")
    public ResponseDto list() {
        List<SiteConfigDto> dto = siteConfigService.list(null,false);
        ResponseDto responseDto = new ResponseDto(dto);
        return responseDto;
    }

    @PostMapping("/saveList")
    public ResponseDto saveList(@RequestBody SiteConfigDto[] SiteConfigDto) {
        siteConfigService.saveList(Arrays.asList(SiteConfigDto));
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param siteConfigDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  SiteConfigDto siteConfigDto) {
        // 保存校验
        ValidatorUtil.length(siteConfigDto.getConfigKey(), "站点设置key", 1, 255);
        ValidatorUtil.length(siteConfigDto.getConfigName(), "站点设置名称", 1, 255);
        ValidatorUtil.length(siteConfigDto.getConfigTip(), "站点设置提示", 1, 255);

        siteConfigService.save(siteConfigDto);
        ResponseDto responseDto = new ResponseDto(siteConfigDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        siteConfigService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }


}
