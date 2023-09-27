package com.windcoder.qycms.system.repository.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface MyUserMapper {
    Map<Object, Object> findSiteAdminUserInfo();
    Map<Object, Object> findUserAndRoleInfoById(@Param("userId") Long userid);
}
