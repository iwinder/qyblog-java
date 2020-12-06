package com.windcoder.qycms.file.service.impl;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.windcoder.qycms.file.service.FileUploadService;
import org.springframework.web.multipart.MultipartFile;

public class QiNiuFileUploadServiceImpl implements FileUploadService {
    public void uploadFile(final MultipartFile file) {
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);

    }
}
