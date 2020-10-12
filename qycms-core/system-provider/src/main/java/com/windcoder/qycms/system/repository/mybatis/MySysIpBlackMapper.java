package com.windcoder.qycms.system.repository.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MySysIpBlackMapper {
    List<String> findAllIpBlackIp();
    void updateDeleted(@Param("deletedStatus") Boolean deletedStatus, @Param("ids")Long[] ids);
}
