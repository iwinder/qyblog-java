package com.windcoder.qycms.file.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.file.entity.FileLibConfig;
import com.windcoder.qycms.file.entity.FileLibConfigExample;
import com.windcoder.qycms.file.dto.FileLibConfigDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.file.repository.mybatis.FileLibConfigMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class FileLibConfigService {
    @Resource
    private FileLibConfigMapper fileLibConfigMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    public static final String PREFIX_FILE_LIBLE_CONFIG = "qy:fileLibConfig";
    public static final String PREFIX_FILE_LIBLE_CONFIG_KEY = "type";
    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        FileLibConfigExample fileLibConfigExample = new FileLibConfigExample();
        List<FileLibConfig> fileLibConfigs = fileLibConfigMapper.selectByExample(fileLibConfigExample);
        PageInfo<FileLibConfig> pageInfo = new PageInfo<>(fileLibConfigs);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<FileLibConfigDto>>() {}.getType();
        List<FileLibConfigDto> fileLibConfigDtoList = ModelMapperUtils.map(fileLibConfigs, type);
        pageDto.setList(fileLibConfigDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param fileLibConfigDto
     */
    public void save(FileLibConfigDto fileLibConfigDto){
        FileLibConfig fileLibConfig = ModelMapperUtils.map(fileLibConfigDto, FileLibConfig.class);
        if (null == fileLibConfig.getId()) {
            this.inster(fileLibConfig);
        } else {
            this.update(fileLibConfig);
        }
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.put(PREFIX_FILE_LIBLE_CONFIG,PREFIX_FILE_LIBLE_CONFIG_KEY+fileLibConfig.getTypeId(),new JSONObject(fileLibConfig).toString());
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        FileLibConfigExample fileLibConfigExample = new FileLibConfigExample();
        fileLibConfigExample.createCriteria().andIdIn(Arrays.asList(ids));
        fileLibConfigMapper.deleteByExample(fileLibConfigExample);
    }

    /**
     * 根据type获取媒体库配置
     * @param typeId
     * @return
     */
    public FileLibConfig findOneByTypeId(Long typeId) {
        FileLibConfigExample fileLibConfigExample = new FileLibConfigExample();
        fileLibConfigExample.createCriteria().andTypeIdEqualTo(typeId);
        List<FileLibConfig>  configs =  fileLibConfigMapper.selectByExample(fileLibConfigExample);
        if (configs.isEmpty()) {
            return null;
        }
        return configs.get(0);
    }

    public FileLibConfigDto findOneByTypeIdOfAdmin(Long typeId) {
        // 获取 redis 缓存 qy:fileLibConfig:typeId
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String str = (String) ops.get(PREFIX_FILE_LIBLE_CONFIG,PREFIX_FILE_LIBLE_CONFIG_KEY+typeId);

        FileLibConfig config = null;
        if (StringUtils.isBlank(str)) {
            config = findOneByTypeId(typeId);
            if (config==null) {
                return new FileLibConfigDto();
            } else {
                ops.put(PREFIX_FILE_LIBLE_CONFIG,PREFIX_FILE_LIBLE_CONFIG_KEY+typeId,new JSONObject(config).toString());
            }
        } else {
            JSONObject jobj = new JSONObject(str);
            config = changeJsonObjectToFileLibConfig(jobj);
        }

        return ModelMapperUtils.map(config, FileLibConfigDto.class);
    }

    /**
     * 新增
     * @param fileLibConfig
     */
    private void inster(FileLibConfig fileLibConfig){
        Date now = new Date();
        fileLibConfig.setCreatedDate(now);
        fileLibConfig.setLastModifiedDate(now);
        fileLibConfigMapper.insertSelective(fileLibConfig);
    }

    /**
     * 更新
     * @param fileLibConfig
     */
    private void update(FileLibConfig fileLibConfig){
        fileLibConfig.setLastModifiedDate(new Date());
        fileLibConfigMapper.updateByPrimaryKeySelective(fileLibConfig);
    }


    private  FileLibConfig changeJsonObjectToFileLibConfig(JSONObject jobj) {
        FileLibConfig fileLibConfig = new FileLibConfig();
        fileLibConfig.setId(jobj.getLong("id"));
        if(jobj.has("accessKey")) {
            fileLibConfig.setAccessKey(jobj.getString("accessKey"));
        }
        if(jobj.has("secretKey")) {
            fileLibConfig.setSecretKey(jobj.getString("secretKey"));
        }
        if(jobj.has("bucket")) {
            fileLibConfig.setBucket(jobj.getString("bucket"));
        }
        if(jobj.has("domain")) {
            fileLibConfig.setDomain(jobj.getString("domain"));
        }
        if(jobj.has("endpoint")) {
            fileLibConfig.setEndpoint(jobj.getString("endpoint"));
        }
        if(jobj.has("prefix")) {
            fileLibConfig.setPrefix(jobj.getString("prefix"));
        }
        if(jobj.has("typeId")) {
            fileLibConfig.setTypeId(jobj.getLong("typeId"));
        }
        return fileLibConfig;
    }
}
