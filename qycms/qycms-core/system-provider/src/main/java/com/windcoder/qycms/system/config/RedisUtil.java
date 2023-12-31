package com.windcoder.qycms.system.config;

import com.windcoder.qycms.system.enums.IpBlackType;
import com.windcoder.qycms.utils.AgentUserUtil;
import com.windcoder.qycms.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String POST_VIEW_COUNT  = "post:viewCount:";
    public static final String IPBLACK_FREQUENT_ACCESS = "IpBlack:FrequentAccess:";
    public static final int IPBLACK_FREQUENT_LIMIT_NUM = 5;
    public static final int IPBLACK_NOT_FOUNT_LIMIT_NUM = 5;
    public static final String IPBLACK_NOT_FOUNT = "IpBlack:NotFount:";
    public static final String IPBLACK_USERNAME_NOT_FOUNT = "IpBlack:UserNameNotFount:";
    public static final int IPBLACK_USERNAME_NOT_FOUNT_LIMIT_NUM = 2;
    public static final String IPBLACK_TMP_INFO = "IpBlack:TmpInfo";

    public Long addPostViewCount(String key, String value) {
      return redisTemplate.opsForHyperLogLog().add(key, value);
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

    public Long increment(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(1l), Constants.REDIS_DEFAULT_TIMEOUT,Constants.REDIS_DEFAULT_TIMEUNIT);
        if(!flag) {
            return   redisTemplate.opsForValue().increment(key,1);
        } else {
            return 1l;
        }
    }


    public void setIpBlackTmpInfo(JSONObject tmpInfo) {
        ListOperations<String, String> ops = redisTemplate.opsForList();

        ops.leftPush(IPBLACK_TMP_INFO, tmpInfo.toString());
    }
    public void setOpsValue(String key,String value) {
        redisTemplate.opsForValue().set(key,value);
    }
    public String getOpsValue(String key) {
        String tmp = redisTemplate.opsForValue().get(key);
        return tmp;
    }


    public void saveBlack(String ip,String agent,String type,String remarks) {
        JSONObject info = new JSONObject();
        info.put("ip", ip);
        info.put("agent", agent);
        info.put("type", type);
        info.put("remarks", remarks);
        setIpBlackTmpInfo(info);
    }
}
