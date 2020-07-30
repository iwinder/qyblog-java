package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.Menus;
import com.windcoder.qycms.system.entity.MenusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenusMapper {
    long countByExample(MenusExample example);

    int deleteByExample(MenusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Menus record);

    int insertSelective(Menus record);

    List<Menus> selectByExample(MenusExample example);

    Menus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Menus record, @Param("example") MenusExample example);

    int updateByExample(@Param("record") Menus record, @Param("example") MenusExample example);

    int updateByPrimaryKeySelective(Menus record);

    int updateByPrimaryKey(Menus record);
}