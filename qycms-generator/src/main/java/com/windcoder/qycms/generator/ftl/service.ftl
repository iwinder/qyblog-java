package com.windcoder.qycms.${module}.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.${module}.entity.${Domain};
import com.windcoder.qycms.${module}.entity.${Domain}Example;
import com.windcoder.qycms.${module}.dto.${Domain}Dto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.${module}.repository.mybatis.${Domain}Mapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
<#list typeSet as type>
    <#if type=='Date'>
import java.util.Date;
    </#if>
</#list>

@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump == "sort">
         ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<${Domain}Dto>>() {}.getType();
        List<${Domain}Dto> ${domain}DtoList = ModelMapperUtils.map(${domain}s, type);
        pageDto.setList(${domain}DtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param ${domain}Dto
     */
    public void save(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain} = ModelMapperUtils.map(${domain}Dto, ${Domain}.class);
        if (null == ${domain}.getId()) {
            this.inster(${domain});
        } else {
            this.update(${domain});
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.createCriteria().andIdIn(Arrays.asList(ids));
        ${domain}Mapper.deleteByExample(${domain}Example);
    }

    /**
     * 新增
     * @param ${domain}
     */
    private void inster(${Domain} ${domain}){
        <#list typeSet as type>
            <#if type=='Date'>
        Date now = new Date();
            </#if>
        </#list>
        <#list fieldList as field>
            <#if field.nameHump=='createdDate'>
        ${domain}.setCreatedDate(now);
            </#if>
            <#if field.nameHump=='lastModifiedDate'>
        ${domain}.setLastModifiedDate(now);
            </#if>
        </#list>
        ${domain}Mapper.insertSelective(${domain});
    }

    /**
     * 更新
     * @param ${domain}
     */
    private void update(${Domain} ${domain}){
        <#list fieldList as field>
            <#if field.nameHump=='lastModifiedDate'>
        ${domain}.setLastModifiedDate(new Date());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKeySelective(${domain});
    }

}
