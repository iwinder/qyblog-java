package com.windcoder.qycms.system.service;

//import org.apache.shiro.SecurityUtils;


//@Service
public class PermissionService  {
//
//    @Autowired
//    private RoleService roleService;
//
//    public Set<String> findPermissionPrivilegesByUser() {
////        User user = (User) SecurityUtils.getSubject().getPrincipal();
////        List<Permission> permissions = this.repository.findByUserId(user.getId());
//        Set<String> privilegeSet = new HashSet<String>();
////        if (permissions != null) {
////            for (Permission permission : permissions) {
////                if (permission.getRole() != null) {
////                    Role role = roleService.findOne(permission.getRole().getId());
////                    if (role != null && role.getPrivileges() != null) {
////                        role.getPrivileges().forEach(p -> privilegeSet.add(p.getIdentifier()));
////                    }
////                } else {
////                    if (permission.getPrivilege() != null) {
////                        privilegeSet.add(permission.getPrivilege().getIdentifier());
////                    }
////                }
////            }
////        }
//        return privilegeSet;
//    }
}
