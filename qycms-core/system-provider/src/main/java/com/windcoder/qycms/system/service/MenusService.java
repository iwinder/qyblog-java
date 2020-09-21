package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.annotation.ServiceLimit;
import com.windcoder.qycms.system.dto.MenusPageDto;
import com.windcoder.qycms.system.dto.MenusWebDto;
import com.windcoder.qycms.system.entity.Menus;
import com.windcoder.qycms.system.entity.MenusExample;
import com.windcoder.qycms.system.dto.MenusDto;
import com.windcoder.qycms.system.repository.mybatis.MenusMapper;

import com.windcoder.qycms.system.repository.mybatis.MyMenusMapper;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
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
public class MenusService {
    @Resource
    private MenusMapper menusMapper;
    @Autowired
    private MyMenusMapper myMenusMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
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
        Integer type = menusDto.getTargetType();
        Menus menus = ModelMapperUtils.map(menusDto, Menus.class);
        if (null == menus.getId()) {
            this.inster(menus);
        } else {
            this.update(menus);
        }
        if (type.intValue() == 1 || type.intValue() == 2) {
            initNowSiteMenus();
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
        menus.setDisplayOrder(getNewDisplayOrder(menus.getParentId()));
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

    public List<Object> findNowHeaderMenus() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String headerObj = (String) ops.get("siteInfo:menus","header");
        if (StringUtils.isBlank(headerObj)) {
            List<MenusWebDto>  headerDto = myMenusMapper.findNowHeaderMenus();
            JSONArray header = new JSONArray(headerDto);
            ops.put("siteInfo:menus","header",header.toString());
            return header.toList();
        } else {
            JSONArray header = new JSONArray(headerObj);
            return header.toList();
        }
    }
    public List<Object> findNowFooterMenus() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String footerObj = (String)ops.get("siteInfo:menus","footer");
        if (StringUtils.isBlank(footerObj)) {
            List<MenusWebDto> footerDto = myMenusMapper.findNowFooterMenus();
            JSONArray jsonArray = new JSONArray(footerDto);
            ops.put("siteInfo:menus","footer", jsonArray.toString());
            return jsonArray.toList();
        } else {
            JSONArray footer = new JSONArray(footerObj);
            return  footer.toList();
        }
    }
    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    public Map<String,List<Object>> findNowAllMenus() {
        List<Object> header =  findNowHeaderMenus();
        List<Object> footer =  findNowFooterMenus();
        Map<String,List<Object>> map = new HashMap<>();
        map.put("header", header);
        map.put("footer", footer);
        return map;
    }

    @Async
    public void initNowSiteMenus() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.delete("siteInfo:menus","header","footer");
    }

    public Integer getNewDisplayOrder(Long parentId) {
       Integer nowOrder =  myMenusMapper.findMaxDisplayOrder(parentId);
       return nowOrder == null ?  1 : (nowOrder + 1);
    }

}
