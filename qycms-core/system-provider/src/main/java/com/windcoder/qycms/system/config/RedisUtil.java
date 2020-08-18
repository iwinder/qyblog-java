package com.windcoder.qycms.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void addPostViewCont(String key, Long value) {
        redisTemplate.opsForHyperLogLog().add(key, String.valueOf(value));
    }


    public Long getPostViewCont(String key) {
        return redisTemplate.opsForHyperLogLog().size(key);
    }
}
