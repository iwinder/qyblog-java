package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.ShortLink;
import com.windcoder.qycms.system.entity.ShortLinkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShortLinkMapper {
    long countByExample(ShortLinkExample example);

    int deleteByExample(ShortLinkExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShortLink record);

    int insertSelective(ShortLink record);

    List<ShortLink> selectByExample(ShortLinkExample example);

    ShortLink selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShortLink record, @Param("example") ShortLinkExample example);

    int updateByExample(@Param("record") ShortLink record, @Param("example") ShortLinkExample example);

    int updateByPrimaryKeySelective(ShortLink record);

    int updateByPrimaryKey(ShortLink record);
}