package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.dto.SiteConfigWebDto;
import com.windcoder.qycms.system.entity.SiteConfig;
import com.windcoder.qycms.system.entity.SiteConfigExample;
import com.windcoder.qycms.system.dto.SiteConfigDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.MySiteConfigMapper;
import com.windcoder.qycms.system.repository.mybatis.SiteConfigMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class SiteConfigService {
    @Resource
    private SiteConfigMapper siteConfigMapper;
    @Autowired
    private MySiteConfigMapper mySiteConfigMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SiteConfigExample siteConfigExample = new SiteConfigExample();
        List<SiteConfig> siteConfigs = siteConfigMapper.selectByExample(siteConfigExample);
        PageInfo<SiteConfig> pageInfo = new PageInfo<>(siteConfigs);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<SiteConfig>>() {}.getType();
        List<SiteConfigDto> siteConfigDtoList = ModelMapperUtils.map(siteConfigs, type);
        pageDto.setList(siteConfigDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param siteConfigDto
     */
    public void save(SiteConfigDto siteConfigDto){
        SiteConfig siteConfig = ModelMapperUtils.map(siteConfigDto, SiteConfig.class);
        if (null == siteConfig.getId()) {
            this.inster(siteConfig);
        } else {
            this.update(siteConfig);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        SiteConfigExample siteConfigExample = new SiteConfigExample();
        siteConfigExample.createCriteria().andIdIn(Arrays.asList(ids));
        siteConfigMapper.deleteByExample(siteConfigExample);
    }

    /**
     * 新增
     * @param siteConfig
     */
    private void inster(SiteConfig siteConfig){
        Date now = new Date();
        siteConfig.setCreatedDate(now);
        siteConfig.setLastModifiedDate(now);
        siteConfigMapper.insert(siteConfig);
    }

    /**
     * 更新
     * @param siteConfig
     */
    private void update(SiteConfig siteConfig){
        siteConfig.setLastModifiedDate(new Date());
        siteConfigMapper.updateByPrimaryKeySelective(siteConfig);
    }



    public List<SiteConfigDto> list(Integer configType,Boolean isWeb) {
        SiteConfigExample siteConfigExample = new SiteConfigExample();
        if (configType != null) {
            siteConfigExample.createCriteria().andTypeEqualTo(configType);
        }
        List<SiteConfig> siteConfigs = siteConfigMapper.selectByExampleWithBLOBs(siteConfigExample);
        Type type = isWeb ?   new TypeToken<List<SiteConfigWebDto>>() {}.getType() : new TypeToken<List<SiteConfigDto>>() {}.getType();
        return ModelMapperUtils.map(siteConfigs, type);
    }

    public void saveList(List<SiteConfigDto> SiteConfigDto) {
        Type type =  new TypeToken<List<SiteConfig>>() {}.getType();
        List<SiteConfig>  siteConfigs = ModelMapperUtils.map(SiteConfigDto, type);
        for (SiteConfig siteConfig: siteConfigs) {
            siteConfig.setLastModifiedDate(new Date());
        }
        mySiteConfigMapper.updateBatch(siteConfigs);
    }
}
