package com.windcoder.qycms.blog.admin.controller;

import com.windcoder.qycms.blog.entity.BlogCategory;
import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.blog.service.BlogCategoryService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/blogCategorys")
@Slf4j
public class BlogCategoryController {

    @Resource
    private BlogCategoryService blogCategoryService;

    public static final String BUSINESS_NAME = "文章分类";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        blogCategoryService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param blogCategoryDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  BlogCategoryDto blogCategoryDto) {
        // 保存校验
        ValidatorUtil.length(blogCategoryDto.getName(), "名称", 1, 255);
        ValidatorUtil.length(blogCategoryDto.getDescription(), "描述", 1, 255);
        ValidatorUtil.length(blogCategoryDto.getKeyWord(), "关键词", 1, 255);
        ValidatorUtil.length(blogCategoryDto.getIdPath(), " id路径", 1, 1000);
        ValidatorUtil.length(blogCategoryDto.getNamePath(), "名称路径", 1, 1000);

        blogCategoryService.save(blogCategoryDto);
        ResponseDto responseDto = new ResponseDto(blogCategoryDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        blogCategoryService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
