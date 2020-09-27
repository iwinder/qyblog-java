package com.windcoder.qycms.system.filters;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.windcoder.qycms.system.repository.mybatis.MySysIpBlackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BloomIpCacheFilter {
    private static BloomFilter<String> bloomIpFilter = null;

    @Autowired
    private MySysIpBlackMapper mySysIpBlackMapper;

    @PostConstruct
    public void init(){
        List<String> list = mySysIpBlackMapper.findAllIpBlackIp();
        bloomIpFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), list.size());
        list.forEach(blogLink ->bloomIpFilter.put(blogLink));
    }
    public static boolean mightContain(String key) {
        return bloomIpFilter.mightContain(key);
    }

    public static BloomFilter<String> getBloomFilter() {
        return bloomIpFilter;
    }
    public static void setBloomFilter(BloomFilter<String> newValue) {
        bloomIpFilter = newValue ;
    }

    public static void refresh(List<String> list) {
        bloomIpFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), list.size());
        list.forEach(blogLink ->bloomIpFilter.put(blogLink));
    }


}
