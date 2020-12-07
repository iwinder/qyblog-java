package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.annotation.IpApiLimit;
import com.windcoder.qycms.system.dto.LinkWebDto;
import com.windcoder.qycms.system.entity.Link;
import com.windcoder.qycms.system.entity.LinkExample;
import com.windcoder.qycms.system.dto.LinkDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.LinkMapper;

import com.windcoder.qycms.system.repository.mybatis.MyLinkMapper;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.json.JSONArray;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class LinkService {
    @Resource
    private LinkMapper linkMapper;
    @Autowired
    private MyLinkMapper myLinkMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

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
        initNowSiteLinks();
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
        linkMapper.insertSelective(link);
    }

    /**
     * 更新
     * @param link
     */
    private void update(Link link){
        link.setLastModifiedDate(new Date());
        linkMapper.updateByPrimaryKeySelective(link);
    }


    @Async
    public void initNowSiteLinks() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.delete("site_link","index","notIndex");
    }
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    public List<Object> findIndexLinkInRedis() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String indexObj = (String)ops.get("site_link","index");
        if (StringUtils.isEmpty(indexObj)) {
            List<LinkWebDto> allWebLink = myLinkMapper.findAllWebLink(true);
            JSONArray index = new JSONArray(allWebLink);
            ops.put("site_link","index",index.toString());
            return index.toList();
        }else {
            JSONArray index = new JSONArray(indexObj);
            return index.toList();
        }
    }
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    public List<Object> findNotIndexLinkInRedis() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String notIndexObj = (String)ops.get("site_link","notIndex");
        if (StringUtils.isEmpty(notIndexObj)) {
            List<LinkWebDto> allWebLink = myLinkMapper.findAllWebLink(false);
            JSONArray notIndex = new JSONArray(allWebLink);
            ops.put("site_link","notIndex",notIndex.toString());
            return notIndex.toList();
        }else {
            JSONArray index = new JSONArray(notIndexObj);
            return index.toList();
        }
    }
    @IpApiLimit(limitType= IpApiLimit.LimitType.IP)
    public List<Object> findAllWebLink() {
        List<Object> indexLink = findIndexLinkInRedis();
        List<Object> notIndexLink = findNotIndexLinkInRedis();
        List<Object> all = new ArrayList<Object>();
        if(indexLink!=null) {
            all.addAll(indexLink);

        }
        if(notIndexLink!=null) {
            all.addAll(notIndexLink);
        }

        return all;
    }


}
