/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.support;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/8
 */
public class SsmException extends RuntimeException {
    public SsmException(String msg) {
        super(msg);
    }

    public SsmException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
