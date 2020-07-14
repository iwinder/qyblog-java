package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.Role;
import com.windcoder.qycms.system.dto.RoleDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.RoleService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/role")
@Slf4j
public class RoleController {

    @Resource
    private RoleService roleService;

    public static final String BUSINESS_NAME = "角色";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        roleService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param roleDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  RoleDto roleDto) {
        // 保存校验
        ValidatorUtil.length(roleDto.getName(), "名称", 1, 255);
        ValidatorUtil.length(roleDto.getRoleType(), "角色类型", 1, 255);
        ValidatorUtil.length(roleDto.getRemark(), "备注", 1, 255);

        roleService.save(roleDto);
        ResponseDto responseDto = new ResponseDto(roleDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        roleService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }


    @GetMapping("list")
    public ResponseDto list() {
        List<RoleDto> roleDtos =  roleService.list();
        ResponseDto responseDto = new ResponseDto(roleDtos);
        return responseDto;
    }
}
