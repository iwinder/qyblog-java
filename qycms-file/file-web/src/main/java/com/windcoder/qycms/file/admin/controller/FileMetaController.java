package com.windcoder.qycms.file.admin.controller;

import com.windcoder.qycms.file.dto.FileMetaPageDto;
import com.windcoder.qycms.file.entity.FileMeta;
import com.windcoder.qycms.file.dto.FileMetaDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.file.service.FileMetaService;
import com.windcoder.qycms.file.service.FileService;
import com.windcoder.qycms.file.service.FileUploadService;
import com.windcoder.qycms.file.service.impl.DefaultFileUploadServiceImpl;
import com.windcoder.qycms.file.service.impl.QiNiuFileUploadServiceImpl;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/fileMeta")
@Slf4j
public class FileMetaController {

    @Resource
    private FileMetaService fileMetaService;
    @Autowired
    private QiNiuFileUploadServiceImpl qiNiuFileUploadService;
    @Autowired
    private DefaultFileUploadServiceImpl defaultFileUploadServiceImpl;
    @Autowired
    private FileService fileService;

    public static final String BUSINESS_NAME = "文件信息表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(FileMetaPageDto pageDto) {
        fileMetaService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param fileMetaDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  FileMetaDto fileMetaDto) {
        // 保存校验
        ValidatorUtil.length(fileMetaDto.getOriginFileName(), "原始名称", 1, 255);
        ValidatorUtil.length(fileMetaDto.getFname(), "文件名", 1, 255);

        fileMetaService.save(fileMetaDto);
        ResponseDto responseDto = new ResponseDto(fileMetaDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        fileMetaService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
    @PostMapping("/upload")
    public ResponseDto defaultUploadFile(@RequestParam MultipartFile file) {
        ResponseDto responseDto = new ResponseDto();
        FileMetaDto metaDto = null;
        FileUploadService uploadFileService = fileService.getUploadFileService();
        metaDto = uploadFileService.uploadFile(file,null);

        responseDto.setContent(metaDto);
        return responseDto;
    }
    @PostMapping("/upload/{typeId}")
    public ResponseDto uploadFile(@RequestParam MultipartFile file, @PathVariable("typeId") Long typeId) {
        ResponseDto responseDto = new ResponseDto();
        FileMetaDto metaDto = null;
        if (typeId!=null ) {
            if(typeId==1) {
                metaDto = defaultFileUploadServiceImpl.uploadFile(file,typeId);
            } else if (typeId==2) {
                metaDto = qiNiuFileUploadService.uploadFile(file,typeId);
            }

        } else {
            metaDto = new FileMetaDto();
        }
        responseDto.setContent(metaDto);
        return responseDto;
    }
    @GetMapping("/{atypeId}/list")
    public ResponseDto allList(FileMetaPageDto pageDto, @PathVariable("atypeId") Long atypeId) {
        ResponseDto responseDto = new ResponseDto();
        if (atypeId!=null ) {
            if(atypeId==1) {
                defaultFileUploadServiceImpl.findAllListOfPage(pageDto);
            } else if(atypeId==2) {
                qiNiuFileUploadService.findAllListOfPage(pageDto);
            }

        }
        responseDto.setContent(pageDto);
        return responseDto;
    }
}
