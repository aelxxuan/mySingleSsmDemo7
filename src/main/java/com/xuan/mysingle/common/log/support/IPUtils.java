/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.log.support;

/**
 * 当前用户ip存储
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class IPUtils {

    private static ThreadLocal<String> ipHolder = new ThreadLocal<>();

    public static String getClientIP() {
        return ipHolder.get();
    }

    public static void setClientIP(String ipAddress) {
        ipHolder.set(ipAddress);
    }

    public static void clearClientIp() {
        ipHolder.remove();
    }
}
