package com.xuan.mysingle.common.log.support;

import com.xuan.mysingle.common.log.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/22
 */
@Repository
public interface LogRepository {
    //默认修改符： public abstract
    List<Log> getAll(Map<String, Object> params);

    int count(Map<String, Object> params);

    int add(Log log);
}
