/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 操作redis的类库
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/23
 */
@Component
public class JRedisHelp {
    /**
     * 记录用户开通失败的次数，一个缓存周期内5次有效，超过5次则在此开通周期内部允许开通
     */
    private static final String CACHE_RUNERRORCOUNT = "ssm_runErrorCount";
    private static final int EXPIREMILLSECONDS = 3600000;
    private RedisTemplate<String, Integer> redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置key，value
     *
     * @param key
     * @param value
     */
    public void setRedisValue(String key, Integer value) {
        String redisKey = CACHE_RUNERRORCOUNT + key;
        redisTemplate.opsForValue().set(redisKey, value);
        redisTemplate.expire(redisKey, EXPIREMILLSECONDS, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取Key的value值
     *
     * @param key
     * @return
     */
    public Integer getRedisValue(String key) {
        String redisKey = CACHE_RUNERRORCOUNT + key;
        Integer oldVal = redisTemplate.opsForValue().get(redisKey);
        return oldVal == null ? 0 : oldVal;
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     */
    public void deleteKey(String key) {
        redisTemplate.opsForValue().getOperations().delete(CACHE_RUNERRORCOUNT + key);
    }
}
