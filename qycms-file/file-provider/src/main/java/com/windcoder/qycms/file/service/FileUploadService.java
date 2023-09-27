package com.windcoder.qycms.file.service;

import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.file.dto.FileMetaPageDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    FileMetaDto uploadFile(final MultipartFile file,  Long typeId);
    void findAllListOfPage(FileMetaPageDto pageDto);
}
