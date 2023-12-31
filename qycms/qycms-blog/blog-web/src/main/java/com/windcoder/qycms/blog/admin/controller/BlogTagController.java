package com.windcoder.qycms.blog.admin.controller;

import com.windcoder.qycms.blog.dto.BlogArticlePageDto;
import com.windcoder.qycms.blog.dto.BlogTagBaseDto;
import com.windcoder.qycms.blog.dto.BlogTagPageDto;
import com.windcoder.qycms.blog.entity.BlogTag;
import com.windcoder.qycms.blog.dto.BlogTagDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.blog.service.BlogTagService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/admin/blogTags")
@Slf4j
public class BlogTagController {

    @Resource
    private BlogTagService blogTagService;

    public static final String BUSINESS_NAME = "标签";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(BlogTagPageDto pageDto) {
        blogTagService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }
    @GetMapping("search")
    public ResponseDto search(BlogTagDto tagDto) {
        List<BlogTagBaseDto> blogTagBaseDtos =  blogTagService.search(tagDto);
        ResponseDto responseDto = new ResponseDto(blogTagBaseDtos);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param blogTag
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  BlogTag blogTag) {
        // 保存校验
        ValidatorUtil.length(blogTag.getName(), "名称", 1, 255);

        blogTagService.save(blogTag);
        ResponseDto responseDto = new ResponseDto(blogTag);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        blogTagService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
