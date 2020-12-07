package com.windcoder.qycms.system.web.controller;

import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.annotation.IpApiLimit;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.system.dto.CommentPageDto;
import com.windcoder.qycms.system.dto.CommentWebDto;
import com.windcoder.qycms.system.service.CommentService;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.IpAddressUtil;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/web/comment/{agentTargetId}")
@Slf4j
public class CommentWebController {
    @Resource
    private CommentService commentService;

    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
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
        String agent = AgentUserUtil.getUserAgent(request);
        comment.setAgent(agent);
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
        String agent = AgentUserUtil.getUserAgent(request);
        comment.setAgent(agent);
        CommentWebDto commentWebDto =  commentService.addReply(agentTargetId,parentId,comment,response);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(commentWebDto);
        return responseDto;

    }
    private void checkfield(CommentDto commentDto) {
        ValidatorUtil.require(commentDto.getAuthorName(), "昵称");
        ValidatorUtil.require(commentDto.getAuthorEmail(), "邮箱");
        ValidatorUtil.isEmail(commentDto.getAuthorEmail());
        ValidatorUtil.require(commentDto.getContent(), "内容");
    }
//    @GetMapping(value = "/agent")
//    public CommentAgentDto findCommentAgent(@PathVariable("agentTargetId")Long agentTargetId){
//        CommentAgent agentTarget = agentTargetService.findOne(agentTargetId);
//        return ModelMapperUtils.map(agentTarget, CommentAgentDto.class);
//    }







}
