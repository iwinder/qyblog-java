package com.windcoder.qycms.blog.web;

import com.windcoder.qycms.blog.dto.BlogCategoryWebDto;
import com.windcoder.qycms.blog.service.BlogCategoryService;
import com.windcoder.qycms.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/category")
public class BlogCategoryWebController {
    @Autowired
    private BlogCategoryService blogCategoryService;

    @GetMapping("/{categoryId}")
    public ResponseDto get(@PathVariable("categoryId") Long categoryId) {
        BlogCategoryWebDto dto = blogCategoryService.findByIdForWeb(categoryId);
        ResponseDto responseDto = new ResponseDto(dto);
        return responseDto;
    }

    @GetMapping("/name/{categoryName}")
    public ResponseDto getByName(@PathVariable("categoryName") String categoryName) {
        BlogCategoryWebDto dto = blogCategoryService.findByIdentifierForWeb(categoryName);
        ResponseDto responseDto = new ResponseDto(dto);
        return responseDto;
    }
}
