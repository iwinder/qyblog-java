package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.SiteConfig;
import com.windcoder.qycms.system.entity.SiteConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteConfigMapper {
    long countByExample(SiteConfigExample example);

    int deleteByExample(SiteConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SiteConfig record);

    int insertSelective(SiteConfig record);

    List<SiteConfig> selectByExampleWithBLOBs(SiteConfigExample example);

    List<SiteConfig> selectByExample(SiteConfigExample example);

    SiteConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SiteConfig record, @Param("example") SiteConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") SiteConfig record, @Param("example") SiteConfigExample example);

    int updateByExample(@Param("record") SiteConfig record, @Param("example") SiteConfigExample example);

    int updateByPrimaryKeySelective(SiteConfig record);

    int updateByPrimaryKeyWithBLOBs(SiteConfig record);

    int updateByPrimaryKey(SiteConfig record);
}