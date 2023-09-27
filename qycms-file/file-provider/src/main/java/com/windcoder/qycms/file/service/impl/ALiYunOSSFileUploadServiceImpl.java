package com.windcoder.qycms.file.service.impl;

import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.file.dto.FileMetaPageDto;
import com.windcoder.qycms.file.service.FileUploadService;
import org.springframework.web.multipart.MultipartFile;

public class ALiYunOSSFileUploadServiceImpl implements FileUploadService {
    @Override
    public FileMetaDto uploadFile(MultipartFile file, Long typeId) {
        return null;
    }

    @Override
    public void findAllListOfPage(FileMetaPageDto pageDto) {

    }
}
