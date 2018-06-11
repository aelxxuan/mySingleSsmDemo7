/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.application;

/**
 * 学科网应用
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/28
 */
public class Application {
    /**
     * 应用ID
     */
    private String id;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用url
     */
    private String url;
    /**
     * ssoId 学科网应用在sso系统里的唯一id
     */
    private Integer ssoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSsoId() {
        return ssoId;
    }

    public void setSsoId(Integer ssoId) {
        this.ssoId = ssoId;
    }
}
