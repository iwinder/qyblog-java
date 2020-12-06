package com.windcoder.qycms.file.admin.controller;

import com.windcoder.qycms.file.entity.FileLibConfig;
import com.windcoder.qycms.file.dto.FileLibConfigDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.file.service.FileLibConfigService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/fileLibConfig")
@Slf4j
public class FileLibConfigController {

    @Resource
    private FileLibConfigService fileLibConfigService;

    public static final String BUSINESS_NAME = "媒体库类型配置";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        fileLibConfigService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param fileLibConfigDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  FileLibConfigDto fileLibConfigDto) {
        // 保存校验
        ValidatorUtil.length(fileLibConfigDto.getAccessKey(), "密钥AccessKey", 1, 255);
        ValidatorUtil.length(fileLibConfigDto.getSecretKey(), "密钥SecretKey", 1, 255);
        ValidatorUtil.length(fileLibConfigDto.getBucket(), "存储空间", 1, 255);
        ValidatorUtil.length(fileLibConfigDto.getEndpoint(), "绑定域名", 1, 255);
        ValidatorUtil.length(fileLibConfigDto.getPrefix(), "前缀", 1, 255);

        fileLibConfigService.save(fileLibConfigDto);
        ResponseDto responseDto = new ResponseDto(fileLibConfigDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        fileLibConfigService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
    @GetMapping("/byType/{typeId}")
    public ResponseDto findOneByTypeId(@PathVariable("typeId") Long typeId) {
        FileLibConfigDto oneByTypeIdOfAdmin = fileLibConfigService.findOneByTypeIdOfAdmin(typeId);
        ResponseDto responseDto = new ResponseDto(oneByTypeIdOfAdmin);
        return responseDto;
    }
}
