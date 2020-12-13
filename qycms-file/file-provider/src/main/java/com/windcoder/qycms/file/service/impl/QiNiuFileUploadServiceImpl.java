package com.windcoder.qycms.file.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.file.dto.FileMetaPageDto;
import com.windcoder.qycms.file.entity.FileLibConfig;
import com.windcoder.qycms.file.service.FileLibConfigService;
import com.windcoder.qycms.file.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class QiNiuFileUploadServiceImpl implements FileUploadService {
    @Autowired
    private FileLibConfigService fileLibConfigService;

    /**
     * 文件上传
     * @param file
     * @param typeId
     * @return
     */
    public FileMetaDto uploadFile(final MultipartFile file,final Long typeId) {
        // 获取媒体库配置
        FileLibConfig fileLibConfig = getFileLibConfig(typeId);
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = fileLibConfig.getAccessKey();
        String secretKey = fileLibConfig.getSecretKey();
        String bucket = fileLibConfig.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String key = StringUtils.isNotBlank(fileLibConfig.getPrefix())?fileLibConfig.getPrefix()+"/"+file.getOriginalFilename():file.getOriginalFilename();

        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            FileMetaDto metaDto = new FileMetaDto();
            metaDto.setOriginFileName(putRet.key);
            metaDto.setFname(putRet.key);
            metaDto.setDomain(fileLibConfig.getDomain());
            metaDto.setDefUrl(metaDto.getDomain()+"/"+metaDto.getFname());
            return metaDto;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                log.error("文件上传异常",r.bodyString());
                throw new BusinessException("文件上传异常");
            } catch (QiniuException ex2) {
                log.error("文件上传异常获取失败",ex2);
                throw new BusinessException("文件上传异常获取失败");
            }
        }  catch (IOException e) {
            log.error("文件转换异常",e);
            throw new BusinessException("文件转换异常");
        }
    }


    public void findAllListOfPage(FileMetaPageDto pageDto) {
        if (pageDto.getEOFFlag() || pageDto.getTypeId()==null) {
            return;
        }
        FileLibConfig fileLibConfig = getFileLibConfig(pageDto.getTypeId());
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        String accessKey = fileLibConfig.getAccessKey();
        String secretKey = fileLibConfig.getSecretKey();
        String bucket = fileLibConfig.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = StringUtils.isNoneBlank(pageDto.getSearchText())?pageDto.getSearchText():"";

        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = pageDto.getSize();
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        String marker = StringUtils.isBlank(pageDto.getMarker())?null:pageDto.getMarker();
        //列举空间文件列表
        try {
            FileListing fileListing = bucketManager.listFilesV2(bucket,prefix,marker,limit,delimiter);
            FileInfo[] items =  fileListing.items;
            FileMetaDto metaDto = null;
            List<FileMetaDto> dataList = new ArrayList<FileMetaDto>();
            for (FileInfo item : items) {
                metaDto = fileInfoToFileMetaDto(item);
                metaDto.setDomain(fileLibConfig.getDomain());
                metaDto.setDefUrl(metaDto.getDomain()+"/"+metaDto.getFname());
                dataList.add(metaDto);
            }
            pageDto.setMarker(fileListing.marker);
            pageDto.setEOFFlag(fileListing.isEOF());
            pageDto.setList(dataList);
        } catch (QiniuException e) {
            log.error("获取文件列表异常",e);
            throw new BusinessException("获取文件列表异常");
        }

    }


    private FileLibConfig getFileLibConfig(Long type) {
        FileLibConfig fileLibConfig = fileLibConfigService.findOneByTypeIdFromCache(type);
        if (fileLibConfig==null || StringUtils.isBlank(fileLibConfig.getAccessKey())) {
            throw new BusinessException("媒体库配置异常");
        }
        return fileLibConfig;
    }
    private FileMetaDto fileInfoToFileMetaDto(FileInfo item) {
        FileMetaDto metaDto = new FileMetaDto();
        metaDto.setFname(item.key);
        metaDto.setOriginFileName(item.key);
        metaDto.setFsize(item.fsize);
        metaDto.setMimeType(item.mimeType);
        // 1ms = 1000000 ns
        Date date = new Date(item.putTime/10000);
        metaDto.setCreatedDate(date);
        metaDto.setLastModifiedDate(date);
        metaDto.setFhash(item.hash);
        metaDto.setFmd5(item.md5);
        metaDto.setEndUser(item.endUser);
        return metaDto;
    }


}
