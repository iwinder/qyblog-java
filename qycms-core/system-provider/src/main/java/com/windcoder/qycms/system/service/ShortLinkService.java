package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.annotation.ServiceLimit;
import com.windcoder.qycms.system.dto.ShortLinkWebDto;
import com.windcoder.qycms.system.entity.ShortLink;
import com.windcoder.qycms.system.entity.ShortLinkExample;
import com.windcoder.qycms.system.dto.ShortLinkDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.MyShortLinkMapper;
import com.windcoder.qycms.system.repository.mybatis.ShortLinkMapper;

import com.windcoder.qycms.utils.ModelMapperUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class ShortLinkService {
    @Resource
    private ShortLinkMapper shortLinkMapper;
    @Autowired
    private MyShortLinkMapper myShortLinkMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ShortLinkExample shortLinkExample = new ShortLinkExample();
        List<ShortLink> shortLinks = shortLinkMapper.selectByExample(shortLinkExample);
        PageInfo<ShortLink> pageInfo = new PageInfo<>(shortLinks);
        pageDto.setTotal(pageInfo.getTotal());
        Type type = new TypeToken<List<ShortLinkDto>>() {}.getType();
        List<ShortLinkDto> shortLinkDtoList = ModelMapperUtils.map(shortLinks, type);
        pageDto.setList(shortLinkDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param shortLinkDto
     */
    public void save(ShortLinkDto shortLinkDto){
        ShortLink shortLink = ModelMapperUtils.map(shortLinkDto, ShortLink.class);
        if (null == shortLink.getId()) {
            this.inster(shortLink);
        } else {
            this.update(shortLink);
        }
        initNowSiteShortLinks();
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        ShortLinkExample shortLinkExample = new ShortLinkExample();
        shortLinkExample.createCriteria().andIdIn(Arrays.asList(ids));
        shortLinkMapper.deleteByExample(shortLinkExample);
        initNowSiteShortLinks();
    }

    /**
     * 新增
     * @param shortLink
     */
    private void inster(ShortLink shortLink){
        Date now = new Date();
        shortLink.setCreatedDate(now);
        shortLink.setLastModifiedDate(now);
        shortLinkMapper.insertSelective(shortLink);
    }

    /**
     * 更新
     * @param shortLink
     */
    private void update(ShortLink shortLink){
        shortLink.setLastModifiedDate(new Date());
        shortLinkMapper.updateByPrimaryKeySelective(shortLink);
    }

    @Async
    public void initNowSiteShortLinks() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        List<ShortLinkWebDto> allWebDto = myShortLinkMapper.findAllWebDto();
        Map<String, Object> linkGo = new HashMap<>();
        for (ShortLinkWebDto shortLink: allWebDto) {
            linkGo.put(shortLink.getIdentifier(), shortLink.getUrl());
        }
        ops.putAll("site_go", linkGo);
    }
//    @ServiceLimit(limitType= ServiceLimit.LimitType.IP)
    public Map<Object, Object> findAllShortWebDto() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        Map<Object, Object> linkInfo = ops.entries("site_go");
        if(linkInfo==null || linkInfo.isEmpty()) {
            initNowSiteShortLinks();
            linkInfo  = ops.entries("site_go");
        }
        return linkInfo;
    }

}
