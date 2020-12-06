package com.windcoder.qycms.file.repository.mybatis;

import com.windcoder.qycms.file.entity.FileLibConfig;
import com.windcoder.qycms.file.entity.FileLibConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileLibConfigMapper {
    long countByExample(FileLibConfigExample example);

    int deleteByExample(FileLibConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileLibConfig record);

    int insertSelective(FileLibConfig record);

    List<FileLibConfig> selectByExample(FileLibConfigExample example);

    FileLibConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileLibConfig record, @Param("example") FileLibConfigExample example);

    int updateByExample(@Param("record") FileLibConfig record, @Param("example") FileLibConfigExample example);

    int updateByPrimaryKeySelective(FileLibConfig record);

    int updateByPrimaryKey(FileLibConfig record);
}