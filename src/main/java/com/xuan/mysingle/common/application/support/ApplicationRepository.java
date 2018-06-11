package com.xuan.mysingle.common.application.support;

import com.xuan.mysingle.common.application.Application;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/28
 */
@Repository
public interface ApplicationRepository {
    List<Application> getAll();
    Application getById(String id);
    int add(Application application);
    int update(Application application);
    int delete(String id);
    int getCountByPage(Map<String,Object> map);
    List<Application> getByPage(Map<String,Object> map);
}
