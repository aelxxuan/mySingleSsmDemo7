package com.xuan.mysingle.core.log;

import com.xuan.mysingle.common.log.Log;
import com.xuan.mysingle.common.log.LogType;
import com.xuan.mysingle.common.support.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
public interface LogService {

    List<Log> getAll(Map<String,Object> params, Pageable pageable);

    int count(Map<String,Object> params);

    int add(LogType logType, String description);

    int add(LogType logType, String description,String operator);

    /**
     * 添加日志,其中模板中变量值在最后传入
     *
     * @param logType
     * @param logTemplate       日志内容模板,支持%s,%d等占位符
     * @param operator
     * @param templateVarValues 日志内容模板中变量占位符对应的值
     * @return
     */
    int add(LogType logType, String logTemplate, String operator, Object... templateVarValues);
}
