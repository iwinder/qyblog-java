package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.dto.PermissionDto;
import com.windcoder.qycms.system.dto.RoleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyPermissionMapper {
    List<PermissionDto> list();
    RoleDto selectRoleByUserId(@Param("userId") Long userId);
}
