package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.SysLoginLog;
import com.windcoder.qycms.system.entity.SysLoginLogExample;
import com.windcoder.qycms.system.dto.SysLoginLogDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.SysLoginLogMapper;

import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class SysLoginLogService {
    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SysLoginLogExample sysLoginLogExample = new SysLoginLogExample();
        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExample(sysLoginLogExample);
        PageInfo<SysLoginLog> pageInfo = new PageInfo<>(sysLoginLogs);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<SysLoginLogDto>>() {}.getType();
        List<SysLoginLogDto> sysLoginLogDtoList = ModelMapperUtils.map(sysLoginLogs, type);
        pageDto.setList(sysLoginLogDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param sysLoginLogDto
     */
    public void save(SysLoginLogDto sysLoginLogDto){
        SysLoginLog sysLoginLog = ModelMapperUtils.map(sysLoginLogDto, SysLoginLog.class);
        saveLog(sysLoginLog);
    }

    public void saveLog(SysLoginLog sysLoginLog) {
        if (null == sysLoginLog.getId()) {
            this.inster(sysLoginLog);
        } else {
            this.update(sysLoginLog);
        }
    }

    @Async
    public void saveLog(String username) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setUserIp(IpAddressUtil.getClientRealIp());
        sysLoginLog.setUserAgent(AgentUserUtil.getUserAgent());
        this.inster(sysLoginLog);
    }
    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        SysLoginLogExample sysLoginLogExample = new SysLoginLogExample();
        sysLoginLogExample.createCriteria().andIdIn(Arrays.asList(ids));
        sysLoginLogMapper.deleteByExample(sysLoginLogExample);
    }

    /**
     * 新增
     * @param sysLoginLog
     */
    private void inster(SysLoginLog sysLoginLog){
        Date now = new Date();
        sysLoginLog.setCreatedDate(now);
        sysLoginLog.setLastModifiedDate(now);
        sysLoginLogMapper.insertSelective(sysLoginLog);
    }

    /**
     * 更新
     * @param sysLoginLog
     */
    private void update(SysLoginLog sysLoginLog){
        sysLoginLog.setLastModifiedDate(new Date());
        sysLoginLogMapper.updateByPrimaryKeySelective(sysLoginLog);
    }

}
