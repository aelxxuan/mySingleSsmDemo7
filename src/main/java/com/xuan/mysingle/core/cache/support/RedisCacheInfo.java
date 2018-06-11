/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.cache.support;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/23
 */
public class RedisCacheInfo {
    private String name;
    private RedisTemplate template;
    private Long expiration;

    public RedisCacheInfo(String name) {
        this(name, (RedisTemplate)null, (Long)null);
    }

    public RedisCacheInfo(String name, RedisTemplate template) {
        this(name, template, (Long)null);
    }

    public RedisCacheInfo(String name, RedisTemplate template, Long expiration) {
        if(name == null) {
            throw new IllegalArgumentException("Cache name can\'t be null!");
        } else {
            this.name = name;
            this.template = template;
            this.expiration = expiration;
        }
    }

    public String getName() {
        return this.name;
    }

    public RedisTemplate getTemplate() {
        return this.template;
    }

    public void setTemplate(RedisTemplate template) {
        this.template = template;
    }

    public Long getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}
