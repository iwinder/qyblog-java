package com.windcoder.qycms.file.service;

import com.windcoder.qycms.file.factory.FileUploadServiceFactory;
import com.windcoder.qycms.system.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private SiteConfigService siteConfigService;

    public FileUploadService getUploadFileService() {
        String key = siteConfigService.findValueOneByKey("siteInfo:other", "site_default_media_lib");
        return FileUploadServiceFactory.getUploadFileService(Integer.valueOf(key));
    }
}
