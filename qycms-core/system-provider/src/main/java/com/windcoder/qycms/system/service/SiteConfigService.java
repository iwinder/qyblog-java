package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    private UserService userService;

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
        siteConfigMapper.insertSelective(siteConfig);
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

    @Async
    public void setSiteInfoToRedis(List<SiteConfig> siteConfigs) {
        if (siteConfigs == null) {
            SiteConfigExample siteConfigExample = new SiteConfigExample();
            siteConfigs = siteConfigMapper.selectByExampleWithBLOBs(siteConfigExample);
        }
        Map<String, Object> siteBase = new HashMap<>();
        Map<String, Object> siteContent = new HashMap<>();
        Map<String, Object> siteSocial = new HashMap<>();
        Map<String, Object> siteOther = new HashMap<>();
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
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        ops.putAll("siteInfo:base", siteBase);
        ops.putAll("siteInfo:content",siteContent);
        ops.putAll("siteInfo:social",siteSocial);
        ops.putAll("siteInfo:other",siteOther);

    }
//    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    public  Map<Object, Object>  findInfoObjString(Integer configType){
        String key = configTypeToSiteKey(configType);
        if (StringUtils.isBlank(key)) {
            throw  new BusinessException("类型异常");
        }
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        Map<Object, Object> siteInfo  = null;
        if (configType>0) {
            siteInfo =  ops.entries(key);
            if (siteInfo == null || siteInfo.isEmpty()) {
                List<SiteConfig> siteConfigs =   list(configType);
                setSiteInfoToRedis(siteConfigs);
                siteInfo  = ops.entries(key);
            }
        } else {
            Set<String> keys = redisTemplate.keys(key);
            siteInfo  = new HashMap<>();
            for (String okey: keys) {
                siteInfo.putAll(fillSiteInfo(ops, okey));
            }
            siteInfo.putAll(userService.findSiteAdminUserInfo());
        }
        return siteInfo;
    }


    public Map<Object, Object> fillSiteInfo(HashOperations<String, Object, Object> ops, String key) {
        Map<Object, Object> siteInfo  = ops.entries(key);
        if (siteInfo == null || siteInfo.isEmpty()) {
            Integer configType = siteKeyToConfigType(key);
            List<SiteConfig> siteConfigs = list(configType);
            setSiteInfoToRedis(siteConfigs);
            siteInfo  = ops.entries(key);
        }
        return siteInfo;
    }

    public Integer siteKeyToConfigType(String key) {
        Integer configType = null;
        switch (key) {
            case "siteInfo:base":
                configType = 1;
                break;
            case "siteInfo:content":
                configType = 2;
                break;
            case "siteInfo:social":
                configType = 3;
                break;
            case "siteInfo:other":
                configType = 4;
                break;
            default:
                configType = 0;
        }
        return configType;
    }

    public String configTypeToSiteKey(Integer configType) {
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
            default:
                key = "siteInfo:*";
        }
        return key;
    }
}
