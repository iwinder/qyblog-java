package com.windcoder.qycms.${module}.admin.controller;

import com.windcoder.qycms.${module}.entity.${Domain};
import com.windcoder.qycms.${module}.dto.${Domain}Dto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.${module}.service.${Domain}Service;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/${domain}")
@Slf4j
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;

    public static final String BUSINESS_NAME = "${tableNameCn}";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        ${domain}Service.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param ${domain}Dto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  ${Domain}Dto ${domain}Dto) {
        // 保存校验
        <#list fieldList as field>
            <#if field.name !="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort" >
            <#if !field.nullAble>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
            </#if>
            <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
            </#if>
            </#if>
        </#list>

        ${domain}Service.save(${domain}Dto);
        ResponseDto responseDto = new ResponseDto(${domain}Dto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        ${domain}Service.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
