package com.windcoder.qycms.system.filters;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.windcoder.qycms.system.repository.mybatis.MyCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BloomCacheFilter {
    private static BloomFilter<String> bloomFilter = null;
    @Autowired
    private MyCommonMapper myCommonMapper;
    @PostConstruct
    public void init(){
        List<String> list = myCommonMapper.findAllBlogPostLink();
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), list.size());
        list.forEach(blogLink ->bloomFilter.put(blogLink));
    }
    public static boolean mightContain(String key) {
        return bloomFilter.mightContain(key);
    }

    public static BloomFilter<String> getBloomFilter() {
        return bloomFilter;
    }
    public static void setBloomFilter(BloomFilter<String> newValue) {
           bloomFilter = newValue ;
    }

    public static void refresh(List<String> list){
        if (list ==null) {
            return;
        }
        BloomCacheFilter.setBloomFilter( BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), list.size()));
        list.forEach(blogLink ->BloomCacheFilter.getBloomFilter().put(blogLink));
    }


}
