/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.common.log;

import java.time.LocalDateTime;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public class Log {
    /**
     * 主键
     */
    private String id;
    /**
     * 日志类型
     */
    private LogType type;
    /**
     * 详细描述
     */
    private String description;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作人IP
     */
    private String clientIp;
    /**
     * 添加时间
     */
    private LocalDateTime createAt;
    /**
     * 添加时间字符串显示
     */
    private String strCreateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getStrCreateAt() {
        return strCreateAt;
    }

    public void setStrCreateAt(String strCreateAt) {
        this.strCreateAt = strCreateAt;
    }
}
