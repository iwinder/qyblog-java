package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.SysLoginLog;
import com.windcoder.qycms.system.dto.SysLoginLogDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.SysLoginLogService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/sysLoginLog")
@Slf4j
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    public static final String BUSINESS_NAME = "登录日志";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        sysLoginLogService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param sysLoginLogDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  SysLoginLogDto sysLoginLogDto) {
        // 保存校验
        ValidatorUtil.require(sysLoginLogDto.getUsername(), "用户用户名");
        ValidatorUtil.length(sysLoginLogDto.getUsername(), "用户用户名", 1, 50);
        ValidatorUtil.length(sysLoginLogDto.getUserIp(), "用户ip", 1, 255);
        ValidatorUtil.length(sysLoginLogDto.getUserAgent(), "用户客户端", 1, 255);

        sysLoginLogService.save(sysLoginLogDto);
        ResponseDto responseDto = new ResponseDto(sysLoginLogDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        sysLoginLogService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
