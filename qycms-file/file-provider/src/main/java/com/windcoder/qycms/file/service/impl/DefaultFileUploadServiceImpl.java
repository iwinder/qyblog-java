package com.windcoder.qycms.file.service.impl;

import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.file.dto.FileMetaPageDto;
import com.windcoder.qycms.file.service.FileMetaService;
import com.windcoder.qycms.file.service.FileUploadService;
import com.windcoder.qycms.utils.DateUtilZ;
import com.windcoder.qycms.utils.FileUploadProperties;
import com.windcoder.qycms.utils.UuidUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class DefaultFileUploadServiceImpl implements FileUploadService {
    @Autowired
    private FileMetaService fileMetaService;
    @Override
    public FileMetaDto uploadFile(MultipartFile file, Long typeId) {
        FileMetaDto metaDto = new FileMetaDto();
        if (!file.isEmpty()){
            String originFileName = file.getOriginalFilename();
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
            String fmd5 = null;
            try {
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(target));
                fmd5 =  DigestUtils.md5Hex(file.getInputStream());
            } catch (IOException e) {
//                System.out.println("保存文件<"+originFileName+">异常");
                throw new BusinessException("保存文件<"+originFileName+">异常" + e.getMessage());
            }
            metaDto.setExtention(ext);
            metaDto.setOriginFileName(originFileName);
            metaDto.setFsize(fileSize);
            metaDto.setFname(targetFileName);
            metaDto.setRelativePath(relativeUrl);
            metaDto.setFmd5(fmd5);
            metaDto.setMimeType(contentType);
            metaDto.setDefUrl(relativeUrl);
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
}
