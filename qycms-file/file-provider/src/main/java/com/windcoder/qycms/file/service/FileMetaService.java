package com.windcoder.qycms.file.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.file.dto.FileMetaPageDto;
import com.windcoder.qycms.file.entity.FileMeta;
import com.windcoder.qycms.file.entity.FileMetaExample;
import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.file.repository.mybatis.FileMetaMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class FileMetaService {
    @Resource
    private FileMetaMapper fileMetaMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(FileMetaPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        FileMetaExample fileMetaExample = new FileMetaExample();
        FileMetaExample.Criteria criteria = fileMetaExample.createCriteria();
        if (StringUtils.isNoneBlank(pageDto.getSearchText())) {
            criteria.andOriginFileNameLike(pageDto.getSearchText());
        }
        fileMetaExample.setOrderByClause("created_date DESC");
        List<FileMeta> fileMetas = fileMetaMapper.selectByExample(fileMetaExample);
        PageInfo<FileMeta> pageInfo = new PageInfo<>(fileMetas);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileMetaDto> fileMetaDtoList = new ArrayList<FileMetaDto>();
        FileMetaDto metaDto = null;
        for (FileMeta meta: fileMetas) {
            metaDto = fileMetaToDto(meta);
            metaDto.setDefUrl(metaDto.getRelativePath());
            fileMetaDtoList.add(metaDto);
        }
        pageDto.setList(fileMetaDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param fileMetaDto
     */
    public void save(FileMetaDto fileMetaDto){
        FileMeta fileMeta = ModelMapperUtils.map(fileMetaDto, FileMeta.class);
        if (null == fileMeta.getId()) {
            this.inster(fileMeta);
        } else {
            this.update(fileMeta);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        FileMetaExample fileMetaExample = new FileMetaExample();
        fileMetaExample.createCriteria().andIdIn(Arrays.asList(ids));
        fileMetaMapper.deleteByExample(fileMetaExample);
    }

    /**
     * 新增
     * @param fileMeta
     */
    private void inster(FileMeta fileMeta){
        Date now = new Date();
        fileMeta.setCreatedDate(now);
        fileMeta.setLastModifiedDate(now);
        fileMetaMapper.insertSelective(fileMeta);
    }

    /**
     * 更新
     * @param fileMeta
     */
    private void update(FileMeta fileMeta){
        fileMeta.setLastModifiedDate(new Date());
        fileMetaMapper.updateByPrimaryKeySelective(fileMeta);
    }

    private FileMetaDto fileMetaToDto(FileMeta fileMeta) {
        FileMetaDto dto = new FileMetaDto();
        dto.setId(fileMeta.getId());
        dto.setOriginFileName(fileMeta.getOriginFileName());
        dto.setFname(fileMeta.getFname());
        dto.setFmd5(fileMeta.getFmd5());
        dto.setFsize(fileMeta.getFsize());
        dto.setFhash(fileMeta.getFhash());
        dto.setMimeType(fileMeta.getMimeType());
        dto.setRelativePath(fileMeta.getRelativePath());
        dto.setCreatedDate(fileMeta.getCreatedDate());
        dto.setLastModifiedDate(fileMeta.getLastModifiedDate());
        return dto;
    }

}
