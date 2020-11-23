package com.windcoder.qycms.file.repository.mybatis;

import com.windcoder.qycms.file.entity.fileLibType;
import com.windcoder.qycms.file.entity.fileLibTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface fileLibTypeMapper {
    long countByExample(fileLibTypeExample example);

    int deleteByExample(fileLibTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(fileLibType record);

    int insertSelective(fileLibType record);

    List<fileLibType> selectByExample(fileLibTypeExample example);

    fileLibType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") fileLibType record, @Param("example") fileLibTypeExample example);

    int updateByExample(@Param("record") fileLibType record, @Param("example") fileLibTypeExample example);

    int updateByPrimaryKeySelective(fileLibType record);

    int updateByPrimaryKey(fileLibType record);
}