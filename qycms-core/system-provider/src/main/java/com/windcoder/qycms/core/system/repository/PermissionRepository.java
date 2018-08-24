package com.windcoder.qycms.core.system.repository;

import com.windcoder.qycms.core.system.entity.Permission;
import com.windcoder.qycms.core.system.entity.User;

import java.util.List;

public interface PermissionRepository extends SupportRepository<Permission,Long>  {
    List<Permission> findByUserId(Long id);
}
