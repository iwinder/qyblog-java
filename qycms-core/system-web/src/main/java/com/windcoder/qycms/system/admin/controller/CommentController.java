package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.dto.CommentPageDto;
import com.windcoder.qycms.system.entity.Comment;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.CommentService;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public ResponseDto list(CommentPageDto pageDto) {
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
    public ResponseDto save( HttpServletRequest request,
            @RequestBody  CommentDto commentDto) {
        // 保存校验
        ValidatorUtil.require(commentDto.getContent(), "内容");
        if (commentDto.getId() == null) {
            String ip  = IpAddressUtil.getClientRealIp(request);
            commentDto.setAuthorIp(ip);
            String agent = AgentUserUtil.getUserAgent(request);
            commentDto.setAgent(agent);
        }
        commentService.saveComment(commentDto);
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

    /**
     * 更新状态
     * @param ids
     * @param status
     * @return
     */
    @PostMapping(value = "/updateStatus")
    public ResponseDto updateStatus(Long[] ids,String status) {
        commentService.updateStatus(ids, status);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
