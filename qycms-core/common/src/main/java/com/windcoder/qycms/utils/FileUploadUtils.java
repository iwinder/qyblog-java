package com.windcoder.qycms.utils;

import com.windcoder.qycms.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtils {

    public static FileUpload upload(MultipartFile file, String savePath){
        FileUpload fileUpload = null;
        if (!file.isEmpty()){
            String originFileName = file.getOriginalFilename();
            String ext =getExt(originFileName);
            Long fileSize = file.getSize();
            savePath = StringUtils.isEmpty(savePath) ? FileUploadProperties.DEFAULTSAVEPATH : savePath;
            if (!savePath.startsWith("/")){
                savePath = "/" + savePath;
            }
            String targetFileName = UUID.randomUUID().toString() + "." + ext;
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
//                System.out.println("保存文件<"+originFileName+">异常");
                throw new BusinessException("保存文件<"+originFileName+">异常" + e.getMessage());
            }
            fileUpload = new FileUpload();
            fileUpload.setExtention(ext);
            fileUpload.setOriginFileName(originFileName);
            fileUpload.setFileSize(fileSize);
            fileUpload.setFileName(targetFileName);
            fileUpload.setRelativePath(relativeUrl);
        }else{
            throw new BusinessException("文件为空");
        }
        return fileUpload;
    }

    public static FileUpload upload(MultipartFile file) {
        return upload(file, null);
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
