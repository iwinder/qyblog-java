package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.exception.BusinessException;
import com.windcoder.qycms.system.annotation.ServiceLimit;
import com.windcoder.qycms.system.dto.*;
import com.windcoder.qycms.system.entity.Comment;
import com.windcoder.qycms.system.entity.CommentAgent;
import com.windcoder.qycms.system.entity.CommentExample;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.enums.CommentStatus;
import com.windcoder.qycms.system.repository.mybatis.CommentMapper;

import com.windcoder.qycms.system.repository.mybatis.MyCommentMapper;
import com.windcoder.qycms.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
@Slf4j
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Autowired
    private CommentAgentService agentTargetService;
    @Autowired
    private UserService userService;
    @Autowired
    private MyCommentMapper myCommentMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(CommentPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CommentExample commentExample = new CommentExample();
        if (StringUtils.isNotBlank(pageDto.getStatus())) {
            commentExample.createCriteria().andStatusEqualTo(pageDto.getStatus());
        } else {
            commentExample.createCriteria().andStatusNotEqualTo(CommentStatus.REFUSED.name());
        }
        commentExample.setOrderByClause("created_date desc");
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<CommentListDto>>() {}.getType();
        List<CommentListDto> commentDtoList = ModelMapperUtils.map(comments, type);
        fillCommentListDto(commentDtoList);
        pageDto.setList(commentDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param commentDto
     */
    public Comment save(CommentDto commentDto){
        Comment comment = ModelMapperUtils.map(commentDto, Comment.class);
        if (null == comment.getId()) {
            this.inster(comment);
        } else {
            this.update(comment);
        }
        return comment;
    }

    public Comment saveComment(CommentDto commentDto) {
        Comment comment = ModelMapperUtils.map(commentDto, Comment.class);
        if (comment.getId() == null) {
            if (comment.getUserId()!=null) {
                comment.setStatus(CommentStatus.ENROLLED.name());
                UserInfoDto user = userService.findOneUserDto(comment.getUserId());
                comment.setAuthorName(user.getNickname());
                comment.setAuthorEmail(user.getEmail());
                Comment parent = findOne(commentDto.getParentId());
                Long topParentId = (null == parent.getTopParentId()|| parent.getTopParentId().equals(0L)) ? parent.getId() : parent.getTopParentId();
                Integer level = parent.getDepth() + 1;
                comment.setTopParentId(topParentId);
                comment.setDepth(level);
                this.inster(comment);
            }
        } else {
            this.update(comment);
        }
        return comment;
    }

    public void saveCommentFromDb(CommentDto commentDto) {
        Comment comment = ModelMapperUtils.map(commentDto, Comment.class);
        comment.setStatus(CommentStatus.ENROLLED.name());
        if (comment.getUserId()!=null) {
            UserInfoDto user = userService.findOneUserDtoForDb(comment.getUserId());
            comment.setAuthorName(user.getNickname());
            comment.setAuthorEmail(user.getEmail());
        }
        if (commentDto.getParentId() == null || commentDto.getParentId().equals(0L)) {
            comment.setDepth(1);
        } else {
            Comment parent = findOne(commentDto.getParentId());
            Long topParentId = (null == parent.getTopParentId() || parent.getTopParentId().equals(0L)) ? parent.getId() : parent.getTopParentId();
            Integer level = parent.getDepth() + 1;
            comment.setTopParentId(topParentId);
            comment.setDepth(level);
        }

        myCommentMapper.insertSelective(comment);
    }



    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        updateStatus(ids, "REFUSED");
//        CommentExample commentExample = new CommentExample();
//        commentExample.createCriteria().andIdIn(Arrays.asList(ids));
//        commentMapper.deleteByExample(commentExample);
    }

    /**
     * 新增
     * @param comment
     */
    private void inster(Comment comment){
        Date now = new Date();
        if (comment.getCreatedDate()==null) {
            comment.setCreatedDate(now);
        }
       if (comment.getLastModifiedDate() == null) {

           comment.setLastModifiedDate(now);
       }
        commentMapper.insert(comment);
    }

    /**
     * 更新
     * @param comment
     */
    private void update(Comment comment){
        comment.setLastModifiedDate(new Date());
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    public void updateStatus(Long[] ids,String status) {
        Comment comment = null;
        if (ids == null || status == null) {
            return;
        }
        for (Long id: ids) {
            comment = new Comment();
            comment.setId(id);
            comment.setStatus(status);
            update(comment);
        }
    }




    private CommentAgent checkAndGetCommentAgent( Long agentTargetId){
//        SystemCommentSetting sysCommentSetting= systemCommentSettingService.findSysForumSetting();
//        if(null == sysCommentSetting.getIsEnabled() || !sysCommentSetting.getIsEnabled() ) {
//            throw new BusinessException("未开启评论功能，不能进行评论");
//        }
        CommentAgent agentTarget = agentTargetService.findOne(agentTargetId);
        if(null == agentTarget.getEnabled() || !agentTarget.getEnabled()){
            throw new BusinessException("未开启评论功能，不能进行评论");
        }
        return agentTarget;
    }

    public void findTopLevelComments(Long agentTargetId, CommentPageDto pageDto) {
        checkAndGetCommentAgent(agentTargetId);
        findWebComments(agentTargetId, pageDto);
    }

    public void findReplies(Long agentTargetId,Long parentId, CommentPageDto pageDto) {
        checkAndGetCommentAgent(agentTargetId);
        pageDto.setParentId(parentId);
        findWebComments(agentTargetId, pageDto);

    }

    public void findWebComments(Long agentTargetId, CommentPageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(agentTargetId);
        if (pageDto.getParentId() == null) {
            criteria.andParentIdEqualTo(Long.valueOf(0)).andDepthEqualTo(1);
            example.setOrderByClause("created_date DESC");
        } else {
            criteria.andTopParentIdEqualTo(pageDto.getParentId());
            example.setOrderByClause("created_date ASC");
        }
        criteria.andStatusNotEqualTo(CommentStatus.APPLIED.name());
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(example);
        Type type = new TypeToken<List<CommentWebDto>>() {}.getType();
        List<CommentWebDto> commentsDto = ModelMapperUtils.map(comments,type);
        User user = null;
        UserWebDto userDto = null;
        Comment parent = null;
        CommentWebDto parentDto = null;
        Long replyCount = null;
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        pageDto.setTotal(pageInfo.getTotal());
        Long parentId = null;
        for (CommentWebDto commentWebDto : commentsDto) {
//            if (commentWebDto.getUserId()!=null && !commentWebDto.getUserId().equals(0)) {
//                user = userService.findOne(commentWebDto.getUserId());
//                userDto = new UserWebDto(user.getId(), user.getNickname(), user.getAvatar());
//                commentWebDto.setUser(userDto);
//            }
            if (commentWebDto.getStatus().equalsIgnoreCase(CommentStatus.REFUSED.name())) {
                commentWebDto.setContent("该评论已被删除");
            }
            if ((commentWebDto.getParentId()!=null &&commentWebDto.getParentId().longValue()>0) || (commentWebDto.getParent()!=null
                    && commentWebDto.getParent().getId() !=null && commentWebDto.getParent().getId().longValue()>0)) {
                if (commentWebDto.getParentId()!=null) {
                    parentId = commentWebDto.getParentId();
                } else {
                    parentId = commentWebDto.getParent().getId();
                }
                parent = findOne(parentId);
                parentDto = new CommentWebDto();
                parentDto.setId(parent.getId());
                parentDto.setAuthorName(parent.getAuthorName());
                parentDto.setAuthorEmail(StringUtilZ.getMD5(parent.getAuthorEmail()));
                parentDto.setAuthorUrl(parent.getAuthorUrl());
                commentWebDto.setParent(parentDto);
            } else  {
                replyCount =  countByTopParentId(commentWebDto.getId());
                commentWebDto.setReplyCount(replyCount.intValue());
            }
            if(commentWebDto.getUser()!=null && commentWebDto.getUser().getId()!=null &&commentWebDto.getUser().getId().longValue()>0 ) {
                UserWebDto oneUserWebDto = userService.findOneUserWebDto(commentWebDto.getUser().getId());
                commentWebDto.setUser(oneUserWebDto);
            }
            commentWebDto.setAuthorEmail(StringUtilZ.getMD5(commentWebDto.getAuthorEmail()));
        }

        pageDto.setList(commentsDto);
    }

    private Comment findOne(Long id) {
        return commentMapper.selectByPrimaryKey(id);
    }


    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    public CommentWebDto addTopLevelComment(Long agentTargetId, CommentDto commentDto, HttpServletResponse response) {
        CommentAgent agentTarget = checkAndGetCommentAgent(agentTargetId);
        commentDto.setTargetId(agentTarget.getId());
        commentDto.setDepth(1);
        String targetName = commentDto.getTargetName();
        checkAndEditStatusIsENROLLED(commentDto);
        Date now = new Date();
        commentDto.setCreatedDate(now);
        commentDto.setLastModifiedDate(now);
        Comment comment =  save(commentDto);
        CommentWebDto commentwebDto = ModelMapperUtils.map(comment,CommentWebDto.class);
        checkAndEditCommentAgentName(targetName,agentTarget);
        setCommentAuthorIntoCookie(response,commentDto.getAuthorName(),commentDto.getAuthorEmail(),commentDto.getAuthorUrl());
        return commentwebDto;
    }
    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    public CommentWebDto addReply(Long agentTargetId,Long parentId, CommentDto commentDto, HttpServletResponse response) {
        CommentAgent agentTarget = checkAndGetCommentAgent(agentTargetId);
        Comment parent = findOne(parentId);
        Long topParentId = (null == parent.getTopParentId()) ? parent.getId() : parent.getTopParentId();
        Integer level = parent.getDepth() + 1;

        commentDto.setTargetId(agentTarget.getId());
        commentDto.setParentId(parent.getId());
        commentDto.setTopParentId(topParentId);
        commentDto.setDepth(level);

        checkAndEditStatusIsENROLLED(commentDto);

        Date now = new Date();
        commentDto.setCreatedDate(now);
        commentDto.setLastModifiedDate(now);
        Comment comment =  save(commentDto);
        CommentWebDto commentwebDto = ModelMapperUtils.map(comment,CommentWebDto.class);
        setCommentAuthorIntoCookie(response,commentDto.getAuthorName(),commentDto.getAuthorEmail(),commentDto.getAuthorUrl());
        return commentwebDto;
    }




    private Long countByEmailAndStatusEnrolled(String authorEmail) {
        CommentExample example = new CommentExample();
        example.createCriteria().andAuthorEmailEqualTo(authorEmail).andStatusEqualTo(CommentStatus.ENROLLED.name());
        return commentMapper.countByExample(example);
    }

    private Long countByEmail(String authorEmail) {
        CommentExample example = new CommentExample();
        example.createCriteria().andAuthorEmailEqualTo(authorEmail);
        return commentMapper.countByExample(example);
    }
    private Long countByTopParentId(Long parentId) {
        CommentExample example = new CommentExample();
        example.createCriteria().andTopParentIdEqualTo(parentId);
        return commentMapper.countByExample(example);
    }


    @Async
    public void setCommentAuthorIntoCookie(HttpServletResponse response, String author, String email, String url)  {
        try {
            CookieUtils.setCookie(response,"comment_remember_author", URLEncoder.encode(author, "UTF-8"), CookieUtils.DEFAULTMAXAGE);
            CookieUtils.setCookie(response,"comment_remember_mail", URLDecoder.decode(email, "UTF-8"),  CookieUtils.DEFAULTMAXAGE);
            if (StringUtils.isNotBlank(url)) {
                CookieUtils.setCookie(response,"comment_remember_url", URLEncoder.encode(url, "UTF-8"),  CookieUtils.DEFAULTMAXAGE);
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            new BusinessException("Url解码失败");
        }
    }

    @Async
    public void checkAndEditCommentAgentName(String targetName,CommentAgent agentTarget ) {
        if (StringUtils.isNotBlank(targetName) &&
                ( StringUtils.isBlank(agentTarget.getTargetName()) ||
                        !targetName.equalsIgnoreCase(agentTarget.getTargetName()))) {
            agentTarget.setTargetName(targetName);
            agentTargetService.save(agentTarget);
        }
    }

    private void checkAndEditStatusIsENROLLED(CommentDto comment){
        Long countByEmail = countByEmail(comment.getAuthorEmail());
        if(countByEmail.longValue()>0){
            Long count = countByEmailAndStatusEnrolled(comment.getAuthorEmail());
            if(count.longValue()>0){
                comment.setStatus(CommentStatus.ENROLLED.name());
            } else {
                comment.setStatus(CommentStatus.APPLIED.name());
            }
        } else {
            comment.setStatus(CommentStatus.APPLIED.name());
        }

    }
    public void fillCommentListDto(List<CommentListDto> listDtos) {
        CommentDto commentDto = null;
        CommentAgentBaseDto agentDto = null;
        UserWebDto userWebDto = null;
        for (CommentListDto dto : listDtos) {
            if (dto.getParent()!=null && dto.getParent().getId()!=null && dto.getParent().getId()>0) {
                Comment one = findOne(dto.getParent().getId());
                commentDto = new CommentDto();
                if (one!=null && one.getId() != null) {
                    commentDto.setId(one.getId());
                    commentDto.setAuthorName(one.getAuthorName());
                    commentDto.setAuthorUrl(one.getAuthorUrl());
                    commentDto.setStatus(one.getStatus());

                } else {
                    commentDto.setId(dto.getParent().getId());
                    commentDto.setAuthorName("网友");
                    commentDto.setContent("内容已删除");
                    commentDto.setStatus(CommentStatus.DELETED.name());
                }
                dto.setParent(commentDto);

            }
            if (dto.getTarget() != null && dto.getTarget().getId()!=null) {
                CommentAgent oneAgent = agentTargetService.findOne(dto.getTarget().getId());
                agentDto = new CommentAgentBaseDto();
                if (oneAgent!=null&&oneAgent.getId()!=null) {
                    agentDto.setId(oneAgent.getId());
                    agentDto.setTargetId(oneAgent.getTargetId());
                    agentDto.setTargetName(oneAgent.getTargetName());
                    agentDto.setTargetType(oneAgent.getTargetType());
                } else {
                    agentDto.setId(dto.getTarget().getId());
                }
                dto.setTarget(agentDto);
            }
            if(dto.getUser()!=null && dto.getUser().getId()!=null && dto.getUser().getId().longValue()>0) {
                userWebDto = userService.findOneUserWebDto(dto.getUser().getId());
                dto.getUser().setAvatar(userWebDto.getAvatar());
                dto.getUser().setNickname(userWebDto.getNickname());
            }
        }
    }
}
