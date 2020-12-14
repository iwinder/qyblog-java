package com.windcoder.qycms.file.factory;



import com.windcoder.qycms.file.service.FileUploadService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileUploadServiceFactory {
    private static final Map<Integer, FileUploadService> uploadFileServiceMap = new ConcurrentHashMap<>();

    /**
     * 获取工厂UploadFileTemplateService
     *
     * @return
     */
    public static FileUploadService getUploadFileService(Integer key) {
        return uploadFileServiceMap.get(key);
    }

    /**
     * 工厂注册
     *
     * @param key
     * @param fileUploadService
     */
    public static void register(final Integer key, final FileUploadService fileUploadService) {
        uploadFileServiceMap.put(key, fileUploadService);
    }
}
