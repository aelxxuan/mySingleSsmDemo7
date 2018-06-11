/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.application;

import com.xuan.mysingle.common.application.Application;
import com.xuan.mysingle.console.support.Pager;

import java.util.List;
import java.util.Map;

/**
 * 应用
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/28
 */
public interface ApplicationService {
    /**
     * 获取所有应用：因为应用的数据不多，所以加入了缓存
     *
     * @return
     */
    List<Application> getApplications();

    /**
     * 通过ID获取应用
     *
     * @param id 应用ID
     * @return
     */
    Application getApplicationById(String id);

    /**
     * 添加应用
     *
     * @param application
     * @return
     */
    boolean addApplication(Application application);

    /**
     * 修改应用
     *
     * @param application
     * @return
     */
    boolean updateApplication(Application application);

    /**
     * 删除应用
     *
     * @param id
     * @return
     */
    boolean deleteApplication(String id);

    /**
     * 根据参数获取应用
     * @param name
     * @param pager
     * @return
     */
    List<Application> getApplicationByParam(String name, Pager pager);

    /**
     * 根据ID获取应用名称
     * @param id 应用ID
     * @return
     */
    String getNameById(String id);

    /**
     * 根据ssoid获取应用
     * @param ssoId ssoid
     * @return
     */
    Application getApplicationBySsoId(String ssoId);
}
