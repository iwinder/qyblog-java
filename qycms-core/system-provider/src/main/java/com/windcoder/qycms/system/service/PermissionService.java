package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.dto.RoleDto;
import com.windcoder.qycms.system.entity.Permission;
import com.windcoder.qycms.system.entity.PermissionExample;
import com.windcoder.qycms.system.dto.PermissionDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.entity.Privilege;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.repository.mybatis.MyPermissionMapper;
import com.windcoder.qycms.system.repository.mybatis.PermissionMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Autowired
    private MyPermissionMapper myPermissionMapper;
    @Autowired
    private RolePrivilegeService rolePrivilegeService;
    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        PermissionExample permissionExample = new PermissionExample();
        List<PermissionDto> permissions = myPermissionMapper.list();
        PageInfo<PermissionDto> pageInfo = new PageInfo<>(permissions);
        pageDto.setTotal(pageInfo.getTotal());
//        List<PermissionDto> permissionDtoList = CopyUtil.copyList(permissions, PermissionDto.class);
        pageDto.setList(permissions);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param permission
     */
    public void save(Permission permission){
        if (null == permission.getId()) {
            this.inster(permission);
        } else {
            this.update(permission);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andIdIn(Arrays.asList(ids));
        permissionMapper.deleteByExample(permissionExample);
    }

    /**
     * 新增
     * @param permission
     */
    private void inster(Permission permission){
        Date now = new Date();
        permission.setCreatedDate(now);
        permission.setLastModifiedDate(now);
        permissionMapper.insert(permission);
    }

    /**
     * 更新
     * @param permission
     */
    private void update(Permission permission){
        permission.setLastModifiedDate(new Date());
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    public RoleDto selectRoleByUserId(Long userId) {
        return myPermissionMapper.selectRoleByUserId(userId);
    }


    public Long selectRoleIdByUserId(Long userId) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andUserIdEqualTo(userId).andRoleIdIsNotNull();
        List<Permission> permissions = permissionMapper.selectByExample(permissionExample);
        if (permissions.isEmpty()) {
            return null;
        }

        return permissions.get(0).getRoleId();

    }

    public List<Permission> selectByUserId(Long userId) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andUserIdEqualTo(userId);
        return permissionMapper.selectByExample(permissionExample);
    }

    public void updateUserRole(Long userId, Long roleId) {
        List<Permission> permissions = selectByUserId(userId);
        if (!permissions.isEmpty()) {
            Permission oldPermission = permissions.get(0);
            if (oldPermission.getRoleId().equals(roleId)) {
                return;
            }
            Long[] ids = {oldPermission.getId()};
            delete(ids);
        }
        Permission permission = new Permission();
        permission.setUserId(userId);
        permission.setRoleId(roleId);
        save(permission);
    }

    public void save(PermissionDto permissionDto) {
    }

    public Set<String> findPermissionPrivilegesByUser(User user) {
        List<Permission> permissions = selectByUserId(user.getId());
        Set<String> privilegeSet = new HashSet<String>();
        Set<String> tmpSet = new HashSet<String>();
        Privilege privilege = new Privilege();
        if (permissions != null) {
            for (Permission permission : permissions) {
                if (permission.getRoleId() != null) {
                    tmpSet = rolePrivilegeService.selectPrivilegeIdentifierListByRoleId(permission.getRoleId());
                    tmpSet.forEach(p -> privilegeSet.add(p));
                } else {

                    if (permission.getPrivilegeId() != null) {
                        privilege =  privilegeService.getOne(permission.getPrivilegeId());
                        privilegeSet.add(privilege.getIdentifier());
                    }
                }
            }
        }
        return privilegeSet;
    }

    public List<String> findPermissionRolesOfUser(User user) {
        return myPermissionMapper.selectRoleNamesByUserId(user.getId());
    }
}
