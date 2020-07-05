package com.windcoder.qycms.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.utils.FileUpload;
import com.windcoder.qycms.utils.FileUploadUtils;
import org.json.JSONObject;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("api/")
public class FileUploadController {

    @PostMapping(value = {"upload/file", "api/admin/upload/file"})
    public ResponseDto uploadFile(@RequestParam(name = "file") MultipartFile file,
                                  @RequestParam(name = "savePath", required = false) String savePath) {
        FileUpload fileUpload = FileUploadUtils.upload(file, savePath);
        ResponseDto result = new ResponseDto();
        result.setContent(fileUpload);
        return result;
    }

    @PostMapping(value = {"api/upload/images"})
    public ResponseDto uploadImageile(@RequestParam(name = "editormd-image-file") MultipartFile file,
                             @RequestParam(name = "savePath", required = false) String savePath) {


        FileUpload fileUpload = FileUploadUtils.upload(file, savePath);
        ResponseDto result = new ResponseDto();
        result.setContent(fileUpload);
        return  result;
    }

}
