package com.welian.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis服务类
 * created by GaoXiang on 2018/1/5
 */
@Service("cloudRedisService")
public class CloudRedisService {
    private static final Logger logger = LoggerFactory.getLogger(CloudRedisService.class);

    private static final String KEY = "cloud:";
    private static final String LOCK_KEY = KEY + "lock:";
    public static final String LOCK_ORDER_EXPIRE_TASK_KEY = LOCK_KEY + "orderExpireTask";
    public static final String LOCK_MESSAGE_REMIND_TASK_KEY = LOCK_KEY + "messageRemindTask";
    public static final String LOCK_WECHAT_PUSH_TASK_KEY = LOCK_KEY + "wechatPushTask";

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisTemplate getRedisTemplate() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer(Long.class));
        return redisTemplate;
    }

    public Long lock(String key, long expiredSecond) {
        Boolean result = redisTemplate.boundValueOps(key).setIfAbsent(expiredSecond);
        result = result == null ? false : true;
        if (result) {
            redisTemplate.boundValueOps(key).set(key, expiredSecond, TimeUnit.SECONDS);
        }
        return result ? expiredSecond : null;
    }

    private boolean unLock(String key, Long lockTime) {
        Boolean delete = redisTemplate.delete(key);
        return delete == null ? false : delete;

    }

    /**
     * 获取redis的时间戳,精确到毫秒
     */
    public long getUnixTime() {
        return (Long) redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::time);
    }

    /**
     * 锁定自定确认操作 ,用于多台机器的schedule
     *
     * @return
     */
    public Long lockTimerTask(String key) {
        try {
            return lock(key, 3600L);
        } catch (Exception e) {
            logger.error("redis锁定任务操作:异常.param={}", LOCK_ORDER_EXPIRE_TASK_KEY, e);
            return null;
        }
    }

    /**
     * 锁定自定确认操作 ,用于多台机器的schedule
     *
     * @return
     */
    public boolean unlockTimerTask(String key, Long lockTime) {
        try {
            return unLock(key, lockTime);
        } catch (Exception e) {
            logger.error("redis锁定定时任务操作:异常.param={}", LOCK_ORDER_EXPIRE_TASK_KEY, e);
            return Boolean.FALSE;
        }
    }

}
