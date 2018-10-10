package com.windcoder.qycms.core.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.windcoder.qycms.utils.FileUpload;
import com.windcoder.qycms.utils.FileUploadUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("api/")
public class FileUploadController {

    @RequestMapping(value = {"upload/file", "api/upload/file"})
    public String uploadFile(@RequestParam(name = "file") MultipartFile file,
                             @RequestParam(name = "savePath", required = false) String savePath) {
        FileUpload fileUpload = FileUploadUtils.upload(file, savePath);
        String result = "";
        try {
            result = Jackson2ObjectMapperBuilder.json().build().writeValueAsString(fileUpload);
        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage(), e);
        }
        return result;
    }

}
