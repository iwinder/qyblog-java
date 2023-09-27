package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.dto.CommentListDto;
import com.windcoder.qycms.system.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCommentMapper {
    int insertSelective(Comment record);
}
