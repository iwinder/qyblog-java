package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.dto.LinkWebDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyLinkMapper {
    List<LinkWebDto> findAllWebLink(@Param("showIndex") Boolean showIndex);
}
