package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.RolePrivilege;
import com.windcoder.qycms.system.entity.RolePrivilegeExample;
import com.windcoder.qycms.system.dto.RolePrivilegeDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.MyRolePrivilegeMapper;
import com.windcoder.qycms.system.repository.mybatis.RolePrivilegeMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class RolePrivilegeService {
    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;
    @Autowired
    private MyRolePrivilegeMapper myRolePrivilegeMapper;


    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RolePrivilegeExample rolePrivilegeExample = new RolePrivilegeExample();
        List<RolePrivilege> rolePrivileges = rolePrivilegeMapper.selectByExample(rolePrivilegeExample);
        PageInfo<RolePrivilege> pageInfo = new PageInfo<>(rolePrivileges);
        pageDto.setTotal(pageInfo.getTotal());
        List<RolePrivilegeDto> rolePrivilegeDtoList = CopyUtil.copyList(rolePrivileges, RolePrivilegeDto.class);
        pageDto.setList(rolePrivilegeDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param rolePrivilegeDto
     */
    public void save(RolePrivilegeDto rolePrivilegeDto){
        RolePrivilege rolePrivilege = CopyUtil.copy(rolePrivilegeDto, RolePrivilege.class);
//        if (null == rolePrivilege.getId()) {
//            this.inster(rolePrivilege);
//        } else {
//            this.update(rolePrivilege);
//        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
//        RolePrivilegeExample rolePrivilegeExample = new RolePrivilegeExample();
//        rolePrivilegeExample.createCriteria().andIdIn(Arrays.asList(ids));
//        rolePrivilegeMapper.deleteByExample(rolePrivilegeExample);
    }

    /**
     * 新增
     * @param rolePrivilege
     */
    private void inster(RolePrivilege rolePrivilege){
        rolePrivilegeMapper.insertSelective(rolePrivilege);
    }

    /**
     * 更新
     * @param rolePrivilege
     */
    private void update(RolePrivilege rolePrivilege){
//        rolePrivilegeMapper.updateByPrimaryKey(rolePrivilege);
    }


    public Set<String> selectPrivilegeIdentifierListByRoleId(Long roleId) {
        return myRolePrivilegeMapper.selectPrivilegeIdentifierListByRoleId(roleId);
    }

}
