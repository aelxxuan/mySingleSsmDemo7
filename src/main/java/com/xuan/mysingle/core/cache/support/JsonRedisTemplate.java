/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.cache.support;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/23
 */
public class JsonRedisTemplate extends RedisTemplate<String, Object> {
    public JsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        this.setHashKeySerializer(stringSerializer);
        this.setHashValueSerializer(stringSerializer);
        this.setKeySerializer(stringSerializer);
        this.setValueSerializer(new JsonRedisSerializer());
        this.setConnectionFactory(redisConnectionFactory);
        this.afterPropertiesSet();
    }
}
