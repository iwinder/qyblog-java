package com.windcoder.qycms.file.admin.controller;

import com.windcoder.qycms.file.entity.fileLibType;
import com.windcoder.qycms.file.dto.fileLibTypeDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.file.service.fileLibTypeService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/fileLibType")
@Slf4j
public class fileLibTypeController {

    @Resource
    private fileLibTypeService fileLibTypeService;

    public static final String BUSINESS_NAME = "媒体库类型表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        fileLibTypeService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param fileLibTypeDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  fileLibTypeDto fileLibTypeDto) {
        // 保存校验
        ValidatorUtil.length(fileLibTypeDto.getName(), "媒体库类型名称", 1, 255);
        ValidatorUtil.length(fileLibTypeDto.getType(), "类型", 1, 255);

        fileLibTypeService.save(fileLibTypeDto);
        ResponseDto responseDto = new ResponseDto(fileLibTypeDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        fileLibTypeService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
