package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.SysIpBlack;
import com.windcoder.qycms.system.entity.SysIpBlackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysIpBlackMapper {
    long countByExample(SysIpBlackExample example);

    int deleteByExample(SysIpBlackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysIpBlack record);

    int insertSelective(SysIpBlack record);

    List<SysIpBlack> selectByExample(SysIpBlackExample example);

    SysIpBlack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysIpBlack record, @Param("example") SysIpBlackExample example);

    int updateByExample(@Param("record") SysIpBlack record, @Param("example") SysIpBlackExample example);

    int updateByPrimaryKeySelective(SysIpBlack record);

    int updateByPrimaryKey(SysIpBlack record);
}