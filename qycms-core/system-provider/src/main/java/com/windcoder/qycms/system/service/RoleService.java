package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.Role;
import com.windcoder.qycms.system.entity.RoleExample;
import com.windcoder.qycms.system.dto.RoleDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.RoleMapper;
import com.windcoder.qycms.utils.CopyUtil;
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
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleExample roleExample = new RoleExample();
        List<Role> roles = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roles, RoleDto.class);
        pageDto.setList(roleDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param roleDto
     */
    public void save(RoleDto roleDto){
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (null == role.getId()) {
            this.inster(role);
        } else {
            this.update(role);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(Arrays.asList(ids));
        roleMapper.deleteByExample(roleExample);
    }

    /**
     * 新增
     * @param role
     */
    private void inster(Role role){
        Date now = new Date();
        role.setCreatedDate(now);
        role.setLastModifiedDate(now);
        roleMapper.insertSelective(role);
    }

    /**
     * 更新
     * @param role
     */
    private void update(Role role){
        role.setLastModifiedDate(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public List<RoleDto> list() {
        RoleExample roleExample = new RoleExample();
        roleExample.setOrderByClause("created_date ASC");
        List<Role> roles = roleMapper.selectByExample(roleExample);
        Type type = new TypeToken<List<RoleDto>>() {}.getType();
        return ModelMapperUtils.map(roles,type);
    }
}
