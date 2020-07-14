package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.Privilege;
import com.windcoder.qycms.system.dto.PrivilegeDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.PrivilegeService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/privilege")
@Slf4j
public class PrivilegeController {

    @Resource
    private PrivilegeService privilegeService;

    public static final String BUSINESS_NAME = "资源";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        privilegeService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param privilegeDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  PrivilegeDto privilegeDto) {
        // 保存校验
        ValidatorUtil.length(privilegeDto.getName(), "资源名称", 1, 255);
        ValidatorUtil.length(privilegeDto.getUrl(), "资源路径", 1, 255);
        ValidatorUtil.length(privilegeDto.getDescription(), "描述，界面UI显示字段", 1, 255);
        ValidatorUtil.length(privilegeDto.getIdentifier(), "权限字符串", 1, 255);
        ValidatorUtil.length(privilegeDto.getType(), "类型", 1, 255);

        privilegeService.save(privilegeDto);
        ResponseDto responseDto = new ResponseDto(privilegeDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        privilegeService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
