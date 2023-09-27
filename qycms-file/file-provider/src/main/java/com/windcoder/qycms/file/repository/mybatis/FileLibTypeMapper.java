package com.windcoder.qycms.file.repository.mybatis;

import com.windcoder.qycms.file.entity.FileLibType;
import com.windcoder.qycms.file.entity.FileLibTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileLibTypeMapper {
    long countByExample(FileLibTypeExample example);

    int deleteByExample(FileLibTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileLibType record);

    int insertSelective(FileLibType record);

    List<FileLibType> selectByExample(FileLibTypeExample example);

    FileLibType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileLibType record, @Param("example") FileLibTypeExample example);

    int updateByExample(@Param("record") FileLibType record, @Param("example") FileLibTypeExample example);

    int updateByPrimaryKeySelective(FileLibType record);

    int updateByPrimaryKey(FileLibType record);
}