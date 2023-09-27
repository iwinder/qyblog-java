package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.dto.SysIpBlackPageDto;
import com.windcoder.qycms.system.entity.SysIpBlack;
import com.windcoder.qycms.system.dto.SysIpBlackDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.SysIpBlackService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
    @RequestMapping("api/admin/sysIpBlack")
@Slf4j
public class SysIpBlackController {

    @Resource
    private SysIpBlackService sysIpBlackService;

    public static final String BUSINESS_NAME = "ip黑名单";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(SysIpBlackPageDto pageDto) {
        sysIpBlackService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param sysIpBlackDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  SysIpBlackDto sysIpBlackDto) {
        // 保存校验
        ValidatorUtil.length(sysIpBlackDto.getVisitorIp(), "访客ip", 1, 255);
        ValidatorUtil.length(sysIpBlackDto.getVisitorAgent(), "访客客户端", 1, 255);
        ValidatorUtil.length(sysIpBlackDto.getType(), "类型", 1, 255);
        ValidatorUtil.length(sysIpBlackDto.getRemarks(), "备注", 1, 255);

        sysIpBlackService.save(sysIpBlackDto);
        ResponseDto responseDto = new ResponseDto(sysIpBlackDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        sysIpBlackService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }


    @GetMapping("/clearBlackInfo")
    public ResponseDto clearBlackInfoOfRedis() {
        sysIpBlackService.clearBlackInfoOfRedis();
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
