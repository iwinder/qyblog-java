package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.MenusAgent;
import com.windcoder.qycms.system.entity.MenusAgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenusAgentMapper {
    long countByExample(MenusAgentExample example);

    int deleteByExample(MenusAgentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MenusAgent record);

    int insertSelective(MenusAgent record);

    List<MenusAgent> selectByExample(MenusAgentExample example);

    MenusAgent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MenusAgent record, @Param("example") MenusAgentExample example);

    int updateByExample(@Param("record") MenusAgent record, @Param("example") MenusAgentExample example);

    int updateByPrimaryKeySelective(MenusAgent record);

    int updateByPrimaryKey(MenusAgent record);
}