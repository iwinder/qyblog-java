package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.Comment;
import com.windcoder.qycms.system.entity.CommentExample;
import com.windcoder.qycms.system.dto.CommentDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.CommentMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CommentExample commentExample = new CommentExample();
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<Comment>>() {}.getType();
        List<CommentDto> commentDtoList = ModelMapperUtils.map(comments, type);
        pageDto.setList(commentDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param commentDto
     */
    public void save(CommentDto commentDto){
        Comment comment = ModelMapperUtils.map(commentDto, Comment.class);
        if (null == comment.getId()) {
            this.inster(comment);
        } else {
            this.update(comment);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdIn(Arrays.asList(ids));
        commentMapper.deleteByExample(commentExample);
    }

    /**
     * 新增
     * @param comment
     */
    private void inster(Comment comment){
        Date now = new Date();
        comment.setCreatedDate(now);
        comment.setLastModifiedDate(now);
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

}
