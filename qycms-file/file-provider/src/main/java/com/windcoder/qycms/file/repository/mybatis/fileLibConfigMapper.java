package com.windcoder.qycms.file.repository.mybatis;

import com.windcoder.qycms.file.entity.fileLibConfig;
import com.windcoder.qycms.file.entity.fileLibConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface fileLibConfigMapper {
    long countByExample(fileLibConfigExample example);

    int deleteByExample(fileLibConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(fileLibConfig record);

    int insertSelective(fileLibConfig record);

    List<fileLibConfig> selectByExample(fileLibConfigExample example);

    fileLibConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") fileLibConfig record, @Param("example") fileLibConfigExample example);

    int updateByExample(@Param("record") fileLibConfig record, @Param("example") fileLibConfigExample example);

    int updateByPrimaryKeySelective(fileLibConfig record);

    int updateByPrimaryKey(fileLibConfig record);
}