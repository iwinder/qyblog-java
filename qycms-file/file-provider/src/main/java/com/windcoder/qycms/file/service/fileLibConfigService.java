package com.windcoder.qycms.file.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.file.entity.fileLibConfig;
import com.windcoder.qycms.file.entity.fileLibConfigExample;
import com.windcoder.qycms.file.dto.fileLibConfigDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.file.repository.mybatis.fileLibConfigMapper;

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
public class fileLibConfigService {
    @Resource
    private fileLibConfigMapper fileLibConfigMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        fileLibConfigExample fileLibConfigExample = new fileLibConfigExample();
        List<fileLibConfig> fileLibConfigs = fileLibConfigMapper.selectByExample(fileLibConfigExample);
        PageInfo<fileLibConfig> pageInfo = new PageInfo<>(fileLibConfigs);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<fileLibConfigDto>>() {}.getType();
        List<fileLibConfigDto> fileLibConfigDtoList = ModelMapperUtils.map(fileLibConfigs, type);
        pageDto.setList(fileLibConfigDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param fileLibConfigDto
     */
    public void save(fileLibConfigDto fileLibConfigDto){
        fileLibConfig fileLibConfig = ModelMapperUtils.map(fileLibConfigDto, fileLibConfig.class);
        if (null == fileLibConfig.getId()) {
            this.inster(fileLibConfig);
        } else {
            this.update(fileLibConfig);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        fileLibConfigExample fileLibConfigExample = new fileLibConfigExample();
        fileLibConfigExample.createCriteria().andIdIn(Arrays.asList(ids));
        fileLibConfigMapper.deleteByExample(fileLibConfigExample);
    }

    /**
     * 新增
     * @param fileLibConfig
     */
    private void inster(fileLibConfig fileLibConfig){
        Date now = new Date();
        fileLibConfig.setCreatedDate(now);
        fileLibConfig.setLastModifiedDate(now);
        fileLibConfigMapper.insertSelective(fileLibConfig);
    }

    /**
     * 更新
     * @param fileLibConfig
     */
    private void update(fileLibConfig fileLibConfig){
        fileLibConfig.setLastModifiedDate(new Date());
        fileLibConfigMapper.updateByPrimaryKeySelective(fileLibConfig);
    }

}
