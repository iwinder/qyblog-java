package com.windcoder.qycms.system.repository.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MyRolePrivilegeMapper {
    Set<String> selectPrivilegeIdentifierListByRoleId(@Param("roleId") Long roleId);

}
