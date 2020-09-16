package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.CommentAgent;
import com.windcoder.qycms.system.entity.CommentAgentExample;
import com.windcoder.qycms.system.dto.CommentAgentDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.CommentAgentMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class CommentAgentService {
    @Resource
    private CommentAgentMapper commentAgentMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CommentAgentExample commentAgentExample = new CommentAgentExample();
        List<CommentAgent> commentAgents = commentAgentMapper.selectByExample(commentAgentExample);
        PageInfo<CommentAgent> pageInfo = new PageInfo<>(commentAgents);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<CommentAgent>>() {}.getType();
        List<CommentAgentDto> commentAgentDtoList = ModelMapperUtils.map(commentAgents, type);
        pageDto.setList(commentAgentDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param commentAgent
     */
    public void save(CommentAgent commentAgent){
        if (null == commentAgent.getId()) {
            this.inster(commentAgent);
        } else {
            this.update(commentAgent);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        CommentAgentExample commentAgentExample = new CommentAgentExample();
        commentAgentExample.createCriteria().andIdIn(Arrays.asList(ids));
        commentAgentMapper.deleteByExample(commentAgentExample);
    }

    /**
     * 新增
     * @param commentAgent
     */
    private void inster(CommentAgent commentAgent){
        Date now = new Date();
        commentAgent.setCreatedDate(now);
        commentAgent.setLastModifiedDate(now);
        commentAgentMapper.insert(commentAgent);
    }

    /**
     * 更新
     * @param commentAgent
     */
    private void update(CommentAgent commentAgent){
        commentAgent.setLastModifiedDate(new Date());
        commentAgentMapper.updateByPrimaryKeySelective(commentAgent);
    }

    /**
     * 根据id获取评论代理
     * @param agentTargetId
     * @return
     */
    public CommentAgent findOne(Long agentTargetId) {
        return commentAgentMapper.selectByPrimaryKey(agentTargetId);
    }
}
