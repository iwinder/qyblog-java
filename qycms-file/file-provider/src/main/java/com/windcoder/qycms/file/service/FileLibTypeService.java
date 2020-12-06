package com.windcoder.qycms.file.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.file.entity.FileLibType;
import com.windcoder.qycms.file.entity.FileLibTypeExample;
import com.windcoder.qycms.file.dto.FileLibTypeDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.file.repository.mybatis.FileLibTypeMapper;

import com.windcoder.qycms.system.enums.MenusAgentType;
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
public class FileLibTypeService {
    @Resource
    private FileLibTypeMapper fileLibTypeMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        FileLibTypeExample fileLibTypeExample = new FileLibTypeExample();
        List<FileLibType> fileLibTypes = fileLibTypeMapper.selectByExample(fileLibTypeExample);
        PageInfo<FileLibType> pageInfo = new PageInfo<>(fileLibTypes);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<FileLibTypeDto>>() {}.getType();
        List<FileLibTypeDto> fileLibTypeDtoList = ModelMapperUtils.map(fileLibTypes, type);
        pageDto.setList(fileLibTypeDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param fileLibTypeDto
     */
    public void save(FileLibTypeDto fileLibTypeDto){
        FileLibType fileLibType = ModelMapperUtils.map(fileLibTypeDto, FileLibType.class);
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
        FileLibTypeExample fileLibTypeExample = new FileLibTypeExample();
        fileLibTypeExample.createCriteria().andIdIn(Arrays.asList(ids));
        fileLibTypeMapper.deleteByExample(fileLibTypeExample);
    }

    public FileLibType findOne(Long id) {
        FileLibType fileLibType = fileLibTypeMapper.selectByPrimaryKey(id);
        return fileLibType;
    }
    public FileLibTypeDto findOneTypeDto(Long id) {
        FileLibType fileLibType = findOne(id);
        if (fileLibType!=null) {
          return   ModelMapperUtils.map(fileLibType, FileLibTypeDto.class);
        }
        return null;
    }
    /**
     * 新增
     * @param fileLibType
     */
    private void inster(FileLibType fileLibType){
        Date now = new Date();
        fileLibType.setCreatedDate(now);
        fileLibType.setLastModifiedDate(now);
        fileLibType.setType(MenusAgentType.USER.name());
        fileLibTypeMapper.insertSelective(fileLibType);
    }

    /**
     * 更新
     * @param fileLibType
     */
    private void update(FileLibType fileLibType){
        fileLibType.setLastModifiedDate(new Date());
        fileLibTypeMapper.updateByPrimaryKeySelective(fileLibType);
    }


}
