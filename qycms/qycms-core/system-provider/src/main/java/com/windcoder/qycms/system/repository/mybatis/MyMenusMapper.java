package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.dto.MenusDto;
import com.windcoder.qycms.system.dto.MenusWebDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyMenusMapper {
    Integer findMaxDisplayOrder(@Param("parentId") Long parentId);
    List<MenusWebDto> findNowHeaderMenus();
    List<MenusWebDto> findNowFooterMenus();
    List<MenusWebDto>  findNowChildByParentId(@Param("parentId") Long parentId);
}
