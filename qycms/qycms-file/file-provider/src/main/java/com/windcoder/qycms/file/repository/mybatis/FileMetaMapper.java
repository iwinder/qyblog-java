package com.windcoder.qycms.file.repository.mybatis;

import com.windcoder.qycms.file.entity.FileMeta;
import com.windcoder.qycms.file.entity.FileMetaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileMetaMapper {
    long countByExample(FileMetaExample example);

    int deleteByExample(FileMetaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileMeta record);

    int insertSelective(FileMeta record);

    List<FileMeta> selectByExample(FileMetaExample example);

    FileMeta selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileMeta record, @Param("example") FileMetaExample example);

    int updateByExample(@Param("record") FileMeta record, @Param("example") FileMetaExample example);

    int updateByPrimaryKeySelective(FileMeta record);

    int updateByPrimaryKey(FileMeta record);
}