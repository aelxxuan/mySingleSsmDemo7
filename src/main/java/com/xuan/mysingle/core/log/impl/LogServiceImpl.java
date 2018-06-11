/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.log.impl;

import com.xuan.mysingle.common.log.Log;
import com.xuan.mysingle.common.log.LogType;
import com.xuan.mysingle.common.log.support.IPUtils;
import com.xuan.mysingle.common.log.support.LogRepository;
import com.xuan.mysingle.common.support.IdGenerator;
import com.xuan.mysingle.common.support.Pageable;
import com.xuan.mysingle.core.log.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogRepository logRepository;
    @Autowired
    IdGenerator idGenerator;

    /**
     * @param params   查询参数{"startTime":"开始时间","endTime":"结束时间","type":"日志类型","operator":"操作人","keyword":"描述关键词"}
     * @param pageable 分页对象
     * @return
     */
    @Override
    public List<Log> getAll(Map<String, Object> params, Pageable pageable) {
        if (params == null) {
            params = new HashMap<>();
        }
        if (pageable != null) {
            params.put("start", pageable.getOffset());
            params.put("length", pageable.getPageSize());
        }
        return logRepository.getAll(params);
    }

    @Override
    public int count(Map<String, Object> params) {
        return logRepository.count(params);
    }

    @Override
    public int add(LogType logType, String description) {
        String oper = "xuanzj";

        return add(logType, description, oper);
    }

    @Override
    public int add(LogType logType, String description, String operator) {
        if (logType == null || StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("日志缺少关键信息，请补充完整。");
        }
        Log log = new Log();
        log.setCreateAt(LocalDateTime.now());
        log.setClientIp(IPUtils.getClientIP());
        log.setDescription(description);
        log.setId(String.valueOf(idGenerator.generateId()));
        log.setOperator(operator);
        log.setType(logType);
        return logRepository.add(log);
    }

    /**
     * @param logType
     * @param logTemplate       日志内容模板,支持%s ,%d等占位符
     * @param operator
     * @param templateVarValues 日志内容模板中变量占位符对应的值
     * @return
     */
    @Override
    public int add(LogType logType, String logTemplate, String operator, Object... templateVarValues) {
        if (templateVarValues != null && templateVarValues.length > 0) {
            logTemplate = String.format(logTemplate, templateVarValues);
        }
        return add(logType,logTemplate,operator);
    }
}
