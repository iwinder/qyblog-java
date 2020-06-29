package com.windcoder.qycms.core.system.service;

import com.windcoder.qycms.core.system.entity.Permission;
import com.windcoder.qycms.core.system.repository.PermissionRepository;
//import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionService  extends BaseService<Permission,Long, PermissionRepository> {

    @Autowired
    private RoleService roleService;

    public Set<String> findPermissionPrivilegesByUser() {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        List<Permission> permissions = this.repository.findByUserId(user.getId());
        Set<String> privilegeSet = new HashSet<String>();
//        if (permissions != null) {
//            for (Permission permission : permissions) {
//                if (permission.getRole() != null) {
//                    Role role = roleService.findOne(permission.getRole().getId());
//                    if (role != null && role.getPrivileges() != null) {
//                        role.getPrivileges().forEach(p -> privilegeSet.add(p.getIdentifier()));
//                    }
//                } else {
//                    if (permission.getPrivilege() != null) {
//                        privilegeSet.add(permission.getPrivilege().getIdentifier());
//                    }
//                }
//            }
//        }
        return privilegeSet;
    }
}
