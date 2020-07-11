package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.Comment;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.CommentService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/comment")
@Slf4j
public class CommentController {

    @Resource
    private CommentService commentService;

    public static final String BUSINESS_NAME = "评论表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        commentService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param commentDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  CommentDto commentDto) {
        // 保存校验
        ValidatorUtil.length(commentDto.getAgent(), "评论者客户端", 1, 255);
        ValidatorUtil.length(commentDto.getAuthorName(), "评论者用户名", 1, 255);
        ValidatorUtil.length(commentDto.getAuthorEmail(), "评论者邮箱", 1, 255);
        ValidatorUtil.length(commentDto.getAuthorIp(), "评论者ip", 1, 255);
        ValidatorUtil.length(commentDto.getAuthorUrl(), "评论者网址", 1, 255);
        ValidatorUtil.length(commentDto.getStatus(), "评论状态", 1, 255);

        commentService.save(commentDto);
        ResponseDto responseDto = new ResponseDto(commentDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        commentService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
