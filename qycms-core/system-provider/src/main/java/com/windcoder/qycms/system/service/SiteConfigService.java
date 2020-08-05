package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.basis.TestService.RediesService;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.dto.SiteConfigWebDto;
import com.windcoder.qycms.system.entity.SiteConfig;
import com.windcoder.qycms.system.entity.SiteConfigExample;
import com.windcoder.qycms.system.dto.SiteConfigDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.MySiteConfigMapper;
import com.windcoder.qycms.system.repository.mybatis.SiteConfigMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class SiteConfigService {
    @Resource
    private SiteConfigMapper siteConfigMapper;
    @Autowired
    private MySiteConfigMapper mySiteConfigMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

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

        List<SiteConfig> siteConfigs = list(configType);
        Type type = isWeb ?   new TypeToken<List<SiteConfigWebDto>>() {}.getType() : new TypeToken<List<SiteConfigDto>>() {}.getType();
        return ModelMapperUtils.map(siteConfigs, type);
    }

    public List<SiteConfig> list(Integer configType) {
        SiteConfigExample siteConfigExample = new SiteConfigExample();
        if (configType != null) {
            siteConfigExample.createCriteria().andTypeEqualTo(configType);
        }
        return siteConfigMapper.selectByExampleWithBLOBs(siteConfigExample);
    }

    public void saveList(List<SiteConfigDto> SiteConfigDto) {
        Type type =  new TypeToken<List<SiteConfig>>() {}.getType();
        List<SiteConfig>  siteConfigs = ModelMapperUtils.map(SiteConfigDto, type);
        for (SiteConfig siteConfig: siteConfigs) {
            siteConfig.setLastModifiedDate(new Date());
        }
        mySiteConfigMapper.updateBatch(siteConfigs);
        setSiteInfoToRedis(null);
    }


    public void setSiteInfoToRedis(List<SiteConfig> siteConfigs) {
        if (siteConfigs == null) {
            SiteConfigExample siteConfigExample = new SiteConfigExample();
            siteConfigs = siteConfigMapper.selectByExampleWithBLOBs(siteConfigExample);
        }
        JSONObject siteBase = new JSONObject();
        JSONObject siteContent = new JSONObject();
        JSONObject siteSocial = new JSONObject();
        JSONObject siteOther = new JSONObject();
        for (SiteConfig siteConfig: siteConfigs) {
            switch (siteConfig.getType().intValue()) {
                case 1:
                    siteBase.put(siteConfig.getConfigKey(), siteConfig.getConfigValue());
                    break;
                case 2:
                    siteContent.put(siteConfig.getConfigKey(), siteConfig.getConfigValue());
                    break;
                case 3:
                    siteSocial.put(siteConfig.getConfigKey(), siteConfig.getConfigValue());
                    break;
                case 4:
                    siteOther.put(siteConfig.getConfigKey(), siteConfig.getConfigValue());
                    break;
            }
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        ops.set("siteInfo:base", siteBase.toString());
        ops.set("siteInfo:content",siteContent.toString());
        ops.set("siteInfo:social",siteSocial.toString());
        ops.set("siteInfo:other",siteOther.toString());

    }

    public JSONObject findInfoObj(Integer configType){
        String key = null;
        switch (configType.intValue()) {
            case 1:
                key = "siteInfo:base";
                break;
            case 2:
                key = "siteInfo:content";
                break;
            case 3:
                key = "siteInfo:social";
                break;
            case 4:
                key = "siteInfo:other";
                break;
        }
        if (StringUtils.isBlank(key)) {
            throw  new BusinessException("类型异常");
        }
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String siteInfo  = ops.get(key);
        if (StringUtils.isBlank(siteInfo)) {
            List<SiteConfig> siteConfigs =   list(1);
            setSiteInfoToRedis(siteConfigs);
            JSONObject siteBase = new JSONObject();
            siteInfo  = ops.get(key);
        }
        JSONObject info = new JSONObject(siteInfo);
        return info;
    }
}
