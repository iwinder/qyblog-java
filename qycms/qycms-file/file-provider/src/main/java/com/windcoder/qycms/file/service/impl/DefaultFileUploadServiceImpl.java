package com.windcoder.qycms.file.service.impl;

import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.file.dto.FileMetaPageDto;
import com.windcoder.qycms.file.entity.FileMeta;
import com.windcoder.qycms.file.factory.FileUploadServiceFactory;
import com.windcoder.qycms.file.service.FileMetaService;
import com.windcoder.qycms.file.service.FileUploadService;
import com.windcoder.qycms.system.service.SiteConfigService;
import com.windcoder.qycms.utils.DateUtilZ;
import com.windcoder.qycms.utils.FileUploadProperties;
import com.windcoder.qycms.utils.UuidUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class DefaultFileUploadServiceImpl implements FileUploadService, InitializingBean {
    @Autowired
    private FileMetaService fileMetaService;
    @Autowired
    private SiteConfigService siteConfigService;
    @Override
    public FileMetaDto uploadFile(MultipartFile file, Long typeId) {
        FileMetaDto metaDto = new FileMetaDto();
        if (!file.isEmpty()){
            String fmd5 = null;
            String originFileName = file.getOriginalFilename();
            try {
                fmd5 =  DigestUtils.md5Hex(file.getInputStream());
                FileMeta oneByMd5 = fileMetaService.findOneByMd5(fmd5);
                if (oneByMd5!=null) {
                   return fileMetaToDto(oneByMd5);
                }
            } catch (IOException e) {
                throw new BusinessException("保存文件<"+originFileName+">时，md5获取异常" + e.getMessage());
            }

            String ext =getExt(originFileName);
            Long fileSize = file.getSize();
            String contentType = file.getContentType();
            String savePath = FileUploadProperties.DEFAULTSAVEPATH ;
            if (!savePath.startsWith("/")){
                savePath = "/" + savePath;
            }
            String targetFileName = UuidUtil.getShortUuid() + "." + ext;
            String basePath = FileUploadProperties.CONTENTPATH;
            String datePath = DateUtilZ.getTimestampOfNowByType("/yyyy/MM/dd/");
            String parentPath = basePath + savePath + datePath;
            String relativeUrl = FileUploadProperties.VIRTUALPATH + savePath + datePath + targetFileName;
            File parentDir = new File(parentPath);
            if (!parentDir.exists()){
                parentDir.mkdirs();
            }
            File target = new File(parentDir.getAbsolutePath(), targetFileName);

            try {
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(target));
            } catch (IOException e) {
                throw new BusinessException("保存文件<"+originFileName+">异常" + e.getMessage());
            }
            String siteUrl = siteConfigService.findValueOneByKey("siteInfo:base","site_url");
            String defUrl = relativeUrl;
            metaDto.setExtention(ext);
            metaDto.setOriginFileName(originFileName);
            metaDto.setFsize(fileSize);
            metaDto.setFname(targetFileName);
            metaDto.setRelativePath(relativeUrl);
            metaDto.setFmd5(fmd5);
            metaDto.setMimeType(contentType);
            metaDto.setDomain(siteUrl);
            if (StringUtils.isNoneBlank(siteUrl)) {
                if (siteUrl.endsWith("/")) {
                    defUrl = siteUrl.substring(0,siteUrl.length()-1)+relativeUrl;
                } else {
                    defUrl = siteUrl + relativeUrl;
                }
            }
            metaDto.setDefUrl(defUrl);
            fileMetaService.save(metaDto);
            return metaDto;
        }else{
            throw new BusinessException("文件为空");
        }

    }

    @Override
    public void findAllListOfPage(FileMetaPageDto pageDto) {
        fileMetaService.list(pageDto);
    }


    private static String getExt(String filePath) {
        if (StringUtils.isBlank(filePath)){
            throw new BusinessException("文件名为空");
        }
        if (!filePath.contains(".")){
            throw new BusinessException("文件名格式错误");
        }
        return filePath.substring(filePath.lastIndexOf(".")+1).toLowerCase();
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

    @Override
    public void afterPropertiesSet() throws Exception {
        FileUploadServiceFactory.register(1,this);
    }
}
