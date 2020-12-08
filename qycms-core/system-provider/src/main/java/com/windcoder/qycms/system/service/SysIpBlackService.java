package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.config.RedisUtil;
import com.windcoder.qycms.system.dto.SysIpBlackPageDto;
import com.windcoder.qycms.system.entity.SysIpBlack;
import com.windcoder.qycms.system.entity.SysIpBlackExample;
import com.windcoder.qycms.system.dto.SysIpBlackDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.system.filters.BloomIpCacheFilter;
import com.windcoder.qycms.system.repository.mybatis.MySysIpBlackMapper;
import com.windcoder.qycms.system.repository.mybatis.SysIpBlackMapper;

import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysIpBlackService {
    @Resource
    private SysIpBlackMapper sysIpBlackMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MySysIpBlackMapper mySysIpBlackMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(SysIpBlackPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SysIpBlackExample sysIpBlackExample = new SysIpBlackExample();
        SysIpBlackExample.Criteria criteria = sysIpBlackExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (StringUtils.isNotBlank(pageDto.getSearchText())) {
            criteria.andVisitorIpLike(pageDto.getSearchText());
        }
        List<SysIpBlack> sysIpBlacks = sysIpBlackMapper.selectByExample(sysIpBlackExample);
        PageInfo<SysIpBlack> pageInfo = new PageInfo<>(sysIpBlacks);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<SysIpBlackDto>>() {}.getType();
        List<SysIpBlackDto> sysIpBlackDtoList = ModelMapperUtils.map(sysIpBlacks, type);
        pageDto.setList(sysIpBlackDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param sysIpBlackDto
     */
    public void save(SysIpBlackDto sysIpBlackDto){

        if (null == sysIpBlackDto.getId()) {
            String[] ips = sysIpBlackDto.getVisitorIp().split("\\r?\\n");
            SysIpBlack sysIpBlack = null;
            for (String ip: ips) {
                sysIpBlack = findOneByIp(ip);
                if (sysIpBlack !=null) {
                    continue;
                }
                sysIpBlack = new SysIpBlack();
                sysIpBlack.setVisitorIp(ip);
                sysIpBlack.setType(IpBlackType.SYSTEM.name());
                sysIpBlack.setRemarks("管理员亲点");
                sysIpBlack.setDeleted(false);
                sysIpBlack.setBlackNum(1);
                this.inster(sysIpBlack);
            }

        } else {
            SysIpBlack sysIpBlack = ModelMapperUtils.map(sysIpBlackDto, SysIpBlack.class);
            this.update(sysIpBlack);
        }
        updateBlackFromRedis();
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        mySysIpBlackMapper.updateDeleted(true, ids);
        updateBlackFromRedis();
    }

    /**
     * 新增
     * @param sysIpBlack
     */
    private void inster(SysIpBlack sysIpBlack){
        Date now = new Date();
        sysIpBlack.setCreatedDate(now);
        sysIpBlack.setLastModifiedDate(now);
        sysIpBlackMapper.insertSelective(sysIpBlack);
    }

    /**
     * 更新
     * @param sysIpBlack
     */
    private void update(SysIpBlack sysIpBlack){
        sysIpBlack.setLastModifiedDate(new Date());
        sysIpBlackMapper.updateByPrimaryKeySelective(sysIpBlack);
    }

    public SysIpBlack findOneByIp(String ip) {
        SysIpBlackExample example = new SysIpBlackExample();
        example.createCriteria().andVisitorIpEqualTo(ip);
        List<SysIpBlack> list = sysIpBlackMapper.selectByExample(example);
        if (list.isEmpty()) {
           return null;
        }
        return list.get(0);
    }

    /**
     * 更新
     */
    public void updateBlackFromRedis() {
        // 查询ip黑名单临时记录
        ListOperations<String, String> ops = redisTemplate.opsForList();
        String tmpStr = ops.rightPop(RedisUtil.IPBLACK_TMP_INFO);
        JSONObject object = null;
        SysIpBlack ipBlack = null;
        List<SysIpBlack> list = new ArrayList<SysIpBlack>();
        while (StringUtils.isNotBlank(tmpStr)) {
            object = new JSONObject(tmpStr);
            ipBlack = new SysIpBlack();
            ipBlack.setVisitorIp(object.getString("ip"));
            ipBlack.setVisitorAgent(object.getString("agent"));
            ipBlack.setType(object.getString("type"));
            ipBlack.setRemarks(object.getString("remarks"));
            ipBlack.setBlackNum(1);
            ipBlack.setDeleted(false);
            list.add(ipBlack);
            tmpStr = ops.rightPop(RedisUtil.IPBLACK_TMP_INFO);
        }
        if (list.size()>0) {
            List<SysIpBlack> newUsers =  list.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(
                            () -> new TreeSet<>(
                                    Comparator.comparing(SysIpBlack::getVisitorIp))), ArrayList::new));
            for (SysIpBlack tIpBlack: newUsers) {
                saveOrUpdate(tIpBlack);
            }
            // 更新布隆过滤器
            List<String> ips = mySysIpBlackMapper.findAllIpBlackIp();
            BloomIpCacheFilter.refresh(ips);
        }

    }

    public void saveOrUpdate(SysIpBlack ipBlack){
        if (ipBlack!=null) {
            SysIpBlack old = findOneByIp(ipBlack.getVisitorIp());
            if (old == null) {
                inster(ipBlack);
            } else {
                ipBlack.setId(old.getId());
                if (old.getType().equalsIgnoreCase(IpBlackType.SYSTEM.name()) ||
                old.getType().equalsIgnoreCase(IpBlackType.LOGIN.name()) || old.getType().equalsIgnoreCase(IpBlackType.NOTFOUNT.name())) {
                    ipBlack.setType(old.getType());
                }
                ipBlack.setDeleted(false);
                ipBlack.setBlackNum(old.getBlackNum() + ipBlack.getBlackNum());
                update(ipBlack);
            }
        }

    }

    /**
     * 清除所有禁止缓存数据
     */
    public void clearBlackInfoOfRedis() {
        Set<String> keys = redisTemplate.keys("IpBlack:*");
        redisTemplate.delete(keys);
    }

}
