package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.Permission;
import com.windcoder.qycms.system.dto.PermissionDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.PermissionService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/permission")
@Slf4j
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    public static final String BUSINESS_NAME = "授权表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        permissionService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param permissionDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  PermissionDto permissionDto) {
        // 保存校验

        permissionService.save(permissionDto);
        ResponseDto responseDto = new ResponseDto(permissionDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        permissionService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
