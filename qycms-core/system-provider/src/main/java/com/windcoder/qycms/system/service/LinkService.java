package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.Link;
import com.windcoder.qycms.system.entity.LinkExample;
import com.windcoder.qycms.system.dto.LinkDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.LinkMapper;

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
public class LinkService {
    @Resource
    private LinkMapper linkMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        LinkExample linkExample = new LinkExample();
        List<Link> links = linkMapper.selectByExample(linkExample);
        PageInfo<Link> pageInfo = new PageInfo<>(links);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<LinkDto>>() {}.getType();
        List<LinkDto> linkDtoList = ModelMapperUtils.map(links, type);
        pageDto.setList(linkDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param linkDto
     */
    public void save(LinkDto linkDto){
        Link link = ModelMapperUtils.map(linkDto, Link.class);
        if (null == link.getId()) {
            this.inster(link);
        } else {
            this.update(link);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        LinkExample linkExample = new LinkExample();
        linkExample.createCriteria().andIdIn(Arrays.asList(ids));
        linkMapper.deleteByExample(linkExample);
    }

    /**
     * 新增
     * @param link
     */
    private void inster(Link link){
        Date now = new Date();
        link.setCreatedDate(now);
        link.setLastModifiedDate(now);
        linkMapper.insert(link);
    }

    /**
     * 更新
     * @param link
     */
    private void update(Link link){
        link.setLastModifiedDate(new Date());
        linkMapper.updateByPrimaryKeySelective(link);
    }

}
