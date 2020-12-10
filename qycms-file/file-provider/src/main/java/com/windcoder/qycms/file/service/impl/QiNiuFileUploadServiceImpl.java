package com.windcoder.qycms.file.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.windcoder.qycms.file.service.FileUploadService;
import org.springframework.web.multipart.MultipartFile;

public class QiNiuFileUploadServiceImpl implements FileUploadService {
    public void uploadFile(final MultipartFile file) {
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);

    }

    public static void findAllList() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        String accessKey = "GS7m9SZJGmNdBCRpdd-N6CTpMtz7_cLijm1JA0r";
        String secretKey = "iMTyYndyFUddqaZ3fJrEffL4AYmf8CEGYfg";
        String bucket = "eibook";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
                String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
                int limit = 10;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
                String delimiter = "";
        //列举空间文件列表
        try {
            FileListing fileListing = bucketManager.listFilesV2(bucket,prefix,null,limit,delimiter);
            System.out.println("fileListing:"+fileListing);
            FileInfo[] items =  fileListing.items;
            for (FileInfo item : items) {
                System.out.println("key : "+item.key);
                System.out.println("hash : "+item.hash);
                System.out.println("fsize : "+item.fsize);
                System.out.println("mimeType : "+item.mimeType);
                System.out.println("putTime : "+item.putTime);
                System.out.println("endUser : "+item.endUser);
                System.out.println("------------");
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }

//        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
//        while (fileListIterator.hasNext()) {
//            //处理获取的file list结果
//            FileInfo[] items = fileListIterator.next();
//            for (FileInfo item : items) {
//                System.out.println("key : "+item.key);
//                System.out.println("hash : "+item.hash);
//                System.out.println("fsize : "+item.fsize);
//                System.out.println("mimeType : "+item.mimeType);
//                System.out.println("putTime : "+item.putTime);
//                System.out.println("endUser : "+item.endUser);
//                System.out.println("------------");
//            }
//        }
    }

    public static void main(String[] args) {
        findAllList();
    }
}
