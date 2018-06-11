/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

import com.xuan.mysingle.common.log.support.IPUtils;
import com.xuan.mysingle.console.common.ClientIpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class SsmHandlerInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(SsmHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //记录每次请求的ip
        String remoteIp = ClientIpUtils.getIpAddress(request);
        IPUtils.setClientIP(remoteIp);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //清空ThreadLocal里记录的当前请求的信息
        IPUtils.clearClientIp();
    }
}
