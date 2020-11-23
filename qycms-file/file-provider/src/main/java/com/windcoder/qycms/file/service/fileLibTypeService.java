package com.windcoder.qycms.file.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.file.entity.fileLibType;
import com.windcoder.qycms.file.entity.fileLibTypeExample;
import com.windcoder.qycms.file.dto.fileLibTypeDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.file.repository.mybatis.fileLibTypeMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class fileLibTypeService {
    @Resource
    private fileLibTypeMapper fileLibTypeMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        fileLibTypeExample fileLibTypeExample = new fileLibTypeExample();
        List<fileLibType> fileLibTypes = fileLibTypeMapper.selectByExample(fileLibTypeExample);
        PageInfo<fileLibType> pageInfo = new PageInfo<>(fileLibTypes);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<fileLibTypeDto>>() {}.getType();
        List<fileLibTypeDto> fileLibTypeDtoList = ModelMapperUtils.map(fileLibTypes, type);
        pageDto.setList(fileLibTypeDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param fileLibTypeDto
     */
    public void save(fileLibTypeDto fileLibTypeDto){
        fileLibType fileLibType = ModelMapperUtils.map(fileLibTypeDto, fileLibType.class);
        if (null == fileLibType.getId()) {
            this.inster(fileLibType);
        } else {
            this.update(fileLibType);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        fileLibTypeExample fileLibTypeExample = new fileLibTypeExample();
        fileLibTypeExample.createCriteria().andIdIn(Arrays.asList(ids));
        fileLibTypeMapper.deleteByExample(fileLibTypeExample);
    }

    /**
     * 新增
     * @param fileLibType
     */
    private void inster(fileLibType fileLibType){
        Date now = new Date();
        fileLibType.setCreatedDate(now);
        fileLibType.setLastModifiedDate(now);
        fileLibType.setType("USER");
        fileLibTypeMapper.insertSelective(fileLibType);
    }

    /**
     * 更新
     * @param fileLibType
     */
    private void update(fileLibType fileLibType){
        fileLibType.setLastModifiedDate(new Date());
        fileLibTypeMapper.updateByPrimaryKeySelective(fileLibType);
    }

}
