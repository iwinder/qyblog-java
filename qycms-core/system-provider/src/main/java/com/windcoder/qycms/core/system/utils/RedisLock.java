package com.windcoder.qycms.core.system.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisLock  implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    protected StringRedisTemplate redisStringTemplate;

    // 存储到redis中的锁标志
    private static final String LOCKED = "ISLOCK";

    // 请求锁的超时时间(ms)
    private static final long TIME_OUT = 30000;

    // 锁的有效时间(s)
    public static final int EXPIRE = 60;

    // 锁标志对应的key;
    private String key;

    // state flag
    private volatile boolean isLocked = false;

    public RedisLock(String key,StringRedisTemplate redisStringTemplate) {
        this.key = key;
        this.redisStringTemplate = redisStringTemplate;
    }


    @Override
    public void lock() {
        // 系统当前时间，毫秒
        long nowTime = System.nanoTime();
        // 请求锁超时时间，毫秒
        long timeout = TIME_OUT * 1000000;
        final Random r = new Random();
        try {
            // 不断循环向Master节点请求锁，当请求时间(System.nanoTime() - nano)超过设定的超时时间则放弃请求锁
            // 这个可以防止一个客户端在某个宕掉的master节点上阻塞过长时间
            // 如果一个master节点不可用了，应该尽快尝试下一个master节点
            while ((System.nanoTime() - nowTime) < timeout) {
                // 将锁作为key存储到redis缓存中，存储成功则获得锁
//                if (redisStringTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(),
//                        LOCKED.getBytes())) {
                if (redisStringTemplate.opsForValue().setIfAbsent(key, LOCKED)) {
                    // 设置锁的有效期，也是锁的自动释放时间，也是一个客户端在其他客户端能抢占锁之前可以执行任务的时间
                    // 可以防止因异常情况无法释放锁而造成死锁情况的发生
                    redisStringTemplate.expire(key, EXPIRE, TimeUnit.SECONDS);
                    isLocked = true;
                    // 上锁成功结束请求
                    break;
                }
                // 获取锁失败时，应该在随机延时后进行重试，避免不同客户端同时重试导致谁都无法拿到锁的情况出现
                Thread.sleep(5L, r.nextInt(500));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        //不管请求锁是否成功，只要已经上锁，客户端都会进行释放锁的操作
        if (isLocked) {
            redisStringTemplate.delete(key);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
