package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.MenusAgent;
import com.windcoder.qycms.system.entity.MenusAgentExample;
import com.windcoder.qycms.system.dto.MenusAgentDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.repository.mybatis.MenusAgentMapper;

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
public class MenusAgentService {
    @Resource
    private MenusAgentMapper menusAgentMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        MenusAgentExample menusAgentExample = new MenusAgentExample();
        List<MenusAgent> menusAgents = menusAgentMapper.selectByExample(menusAgentExample);
        PageInfo<MenusAgent> pageInfo = new PageInfo<>(menusAgents);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<MenusAgentDto>>() {}.getType();
        List<MenusAgentDto> menusAgentDtoList = ModelMapperUtils.map(menusAgents, type);
        pageDto.setList(menusAgentDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param menusAgentDto
     */
    public void save(MenusAgentDto menusAgentDto){
        MenusAgent menusAgent = ModelMapperUtils.map(menusAgentDto, MenusAgent.class);
        if (null == menusAgent.getId()) {
            this.inster(menusAgent);
        } else {
            this.update(menusAgent);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        MenusAgentExample menusAgentExample = new MenusAgentExample();
        menusAgentExample.createCriteria().andIdIn(Arrays.asList(ids));
        menusAgentMapper.deleteByExample(menusAgentExample);
    }

    /**
     * 新增
     * @param menusAgent
     */
    private void inster(MenusAgent menusAgent){
        Date now = new Date();
        menusAgent.setCreatedDate(now);
        menusAgent.setLastModifiedDate(now);
        menusAgentMapper.insertSelective(menusAgent);
    }

    /**
     * 更新
     * @param menusAgent
     */
    private void update(MenusAgent menusAgent){
        menusAgent.setLastModifiedDate(new Date());
        menusAgentMapper.updateByPrimaryKeySelective(menusAgent);
    }


    public MenusAgent findOne(Long agentId) {
        return menusAgentMapper.selectByPrimaryKey(agentId);
    }

    public MenusAgentDto findOneMenusAgentDto(Long agentId) {
        MenusAgent menusAgent = findOne(agentId);
        if (menusAgent == null) {
            return null;
        }
        MenusAgentDto dto = ModelMapperUtils.map(menusAgent, MenusAgentDto.class);
        return dto;
    }
}
