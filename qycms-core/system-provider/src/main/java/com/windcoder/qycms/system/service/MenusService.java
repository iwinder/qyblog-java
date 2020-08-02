package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.dto.MenusAgentDto;
import com.windcoder.qycms.system.dto.MenusPageDto;
import com.windcoder.qycms.system.entity.Menus;
import com.windcoder.qycms.system.entity.MenusExample;
import com.windcoder.qycms.system.dto.MenusDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.MenusMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class MenusService {
    @Resource
    private MenusMapper menusMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(MenusPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        MenusExample menusExample = new MenusExample();
        List<Menus> menuss = menusMapper.selectByExample(menusExample);
        PageInfo<Menus> pageInfo = new PageInfo<>(menuss);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<MenusDto>>() {}.getType();
        List<MenusDto> menusDtoList = ModelMapperUtils.map(menuss, type);
        pageDto.setList(menusDtoList);
    }

    public void listWithChildren(MenusPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        MenusExample menusExample = new MenusExample();
        menusExample.createCriteria().andTargetIdEqualTo(pageDto.getTargetId()).andParentIdIsNull();
        List<Menus> menuss = menusMapper.selectByExample(menusExample);
        PageInfo<Menus> pageInfo = new PageInfo<>(menuss);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<MenusDto>>() {}.getType();
        List<MenusDto> menusDtoList = ModelMapperUtils.map(menuss, type);
        fillChildren(menusDtoList);
        pageDto.setList(menusDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param menusDto
     */
    public void save(MenusDto menusDto){
        Menus menus = ModelMapperUtils.map(menusDto, Menus.class);
        if (null == menus.getId()) {
            this.inster(menus);
        } else {
            this.update(menus);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        MenusExample menusExample = new MenusExample();
        menusExample.createCriteria().andIdIn(Arrays.asList(ids));
        menusMapper.deleteByExample(menusExample);
    }

    /**
     * 新增
     * @param menus
     */
    private void inster(Menus menus){
        Date now = new Date();
        menus.setCreatedDate(now);
        menus.setLastModifiedDate(now);
        menusMapper.insert(menus);
    }

    /**
     * 更新
     * @param menus
     */
    private void update(Menus menus){
        menus.setLastModifiedDate(new Date());
        menusMapper.updateByPrimaryKeySelective(menus);
    }

    public Menus findOne(Long menusId) {
        return menusMapper.selectByPrimaryKey(menusId);
    }

    public MenusDto findOneMenusAgentDto(Long menusId) {
        Menus menus = findOne(menusId);
        if (menus == null) {
            return null;
        }
        return ModelMapperUtils.map(menus, MenusDto.class);
    }

    public List<MenusDto> findParentList(Long agentId) {
        MenusExample example = new MenusExample();
        example.createCriteria().andTargetIdEqualTo(agentId).andParentIdIsNull();
        List<Menus> menus = menusMapper.selectByExample(example);
        Type type = new TypeToken<List<MenusDto>>() {}.getType();
        return ModelMapperUtils.map(menus, type);
    }

    public List<MenusDto> findChildrenList(MenusDto parent) {
        MenusExample example = new MenusExample();
        example.createCriteria().andTargetIdEqualTo(parent.getTargetId()).andParentIdEqualTo(parent.getId());
        List<Menus> menus = menusMapper.selectByExample(example);
        Type type = new TypeToken<List<MenusDto>>() {}.getType();
        return ModelMapperUtils.map(menus, type);
    }

    public void fillChildren(List<MenusDto> menusDtos) {
        List<MenusDto> childen = new ArrayList<>();
        for (MenusDto menusDto : menusDtos) {
            childen = findChildrenList(menusDto);
            menusDto.setChildren(childen);
        }
    }
}
