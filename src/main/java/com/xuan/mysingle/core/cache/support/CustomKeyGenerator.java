/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.cache.support;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

import java.lang.reflect.Method;



/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/23
 */
public class CustomKeyGenerator implements KeyGenerator {
    public CustomKeyGenerator() {
    }
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params).toString();
    }

    public static Object generateKey(Object... params) {
        if(params.length == 0) {
            return SimpleKey.EMPTY;
        } else {
            if(params.length == 1) {
                Object param = params[0];
                if(param != null && !param.getClass().isArray()) {
                    return param;
                }
            }

            return new SimpleKey(params);
        }
    }

}
