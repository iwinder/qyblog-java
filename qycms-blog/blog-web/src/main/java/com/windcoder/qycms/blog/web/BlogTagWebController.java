package com.windcoder.qycms.blog.web;

import com.windcoder.qycms.blog.dto.BlogTagWebDto;
import com.windcoder.qycms.blog.service.BlogTagService;
import com.windcoder.qycms.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/tag")
public class BlogTagWebController {
    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/{tagId}")
    public ResponseDto get(@PathVariable("tagId") Long tagId) {
        BlogTagWebDto dto  = blogTagService.findByIdForWeb(tagId);
        ResponseDto responseDto = new ResponseDto(dto);
        return responseDto;
    }

    @GetMapping("/name/{tagName}")
    public ResponseDto getByName(@PathVariable("tagName") String tagName) {
        BlogTagWebDto dto  = blogTagService.findByIdentifierForWeb(tagName);
        ResponseDto responseDto = new ResponseDto(dto);
        return responseDto;
    }
}
