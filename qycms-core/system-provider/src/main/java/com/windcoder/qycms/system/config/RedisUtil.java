package com.windcoder.qycms.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void addPostViewCount(String key, String value) {
        redisTemplate.opsForHyperLogLog().add(key, value);
    }


    public Long getPostViewCount(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }
    public void delPostViewCount(String key) {
        redisTemplate.opsForHyperLogLog().delete(key);
    }
    public void delPostViewCounts(Set<String> keys) {
        redisTemplate.delete(keys);
    }


    public Set<String> getKeys(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys;
    }
}
