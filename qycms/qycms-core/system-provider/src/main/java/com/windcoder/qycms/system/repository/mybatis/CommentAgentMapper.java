package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.entity.CommentAgent;
import com.windcoder.qycms.system.entity.CommentAgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentAgentMapper {
    long countByExample(CommentAgentExample example);

    int deleteByExample(CommentAgentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommentAgent record);

    int insertSelective(CommentAgent record);

    List<CommentAgent> selectByExample(CommentAgentExample example);

    CommentAgent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommentAgent record, @Param("example") CommentAgentExample example);

    int updateByExample(@Param("record") CommentAgent record, @Param("example") CommentAgentExample example);

    int updateByPrimaryKeySelective(CommentAgent record);

    int updateByPrimaryKey(CommentAgent record);
}