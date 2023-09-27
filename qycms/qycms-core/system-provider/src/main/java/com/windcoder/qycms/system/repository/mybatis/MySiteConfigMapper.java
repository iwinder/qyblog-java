package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.SiteConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MySiteConfigMapper {
    int updateBatch(@Param("siteConfigList")List<SiteConfig> list);
}
