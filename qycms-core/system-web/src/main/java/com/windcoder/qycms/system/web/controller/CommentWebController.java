package com.windcoder.qycms.system.web.controller;

import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.system.dto.CommentPageDto;
import com.windcoder.qycms.system.dto.CommentWebDto;
import com.windcoder.qycms.system.entity.Comment;
import com.windcoder.qycms.system.service.CommentService;
import com.windcoder.qycms.utils.CookieUtils;
import com.windcoder.qycms.utils.IpAddressUtil;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@RequestMapping("api/web/comment/{agentTargetId}")
@Slf4j
public class CommentWebController {
    @Resource
    private CommentService commentService;


    @GetMapping(value = "")
    public ResponseDto list(@PathVariable("agentTargetId") Long agentTargetId,CommentPageDto pageDto) {
        commentService.findTopLevelComments(agentTargetId, pageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping(value = "/add")
    public ResponseDto add(@PathVariable("agentTargetId") Long agentTargetId,
                           @RequestBody CommentDto comment,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        checkfield(comment) ;
        String ip  = IpAddressUtil.getClientRealIp(request);
        comment.setAuthorIp(ip);
        CommentWebDto commentWebDto =  commentService.addTopLevelComment(agentTargetId, comment, response);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(commentWebDto);
        return responseDto;
    }

    @GetMapping(value = "/{commentId}/replies")
    public ResponseDto replies(@PathVariable("agentTargetId") Long agentTargetId,
                                  @PathVariable("commentId") Long parentId,
                                CommentPageDto pageable) {
        commentService.findReplies(agentTargetId, parentId, pageable);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageable);
        return responseDto;
    }

    @PostMapping(value = "/{commentId}/replies/add")
    public ResponseDto addReply(@PathVariable("agentTargetId") Long agentTargetId,
                               @PathVariable("commentId") Long parentId,
                                @RequestBody CommentDto comment,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        checkfield(comment);
        String ip  = IpAddressUtil.getClientRealIp(request);
        comment.setAuthorIp(ip);
        CommentWebDto commentWebDto =  commentService.addReply(agentTargetId,parentId,comment,response);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(commentWebDto);
        return responseDto;

    }


    private void checkfield(CommentDto commentDto) {
        ValidatorUtil.require(commentDto.getAuthorName(), "昵称");
        ValidatorUtil.require(commentDto.getAuthorEmail(), "邮箱");
        ValidatorUtil.isEmail(commentDto.getAuthorEmail());
    }
//    @GetMapping(value = "/agent")
//    public CommentAgentDto findCommentAgent(@PathVariable("agentTargetId")Long agentTargetId){
//        CommentAgent agentTarget = agentTargetService.findOne(agentTargetId);
//        return ModelMapperUtils.map(agentTarget, CommentAgentDto.class);
//    }







}
