/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.application.impl;

import com.xuan.mysingle.common.application.Application;
import com.xuan.mysingle.common.application.support.ApplicationRepository;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.core.application.ApplicationService;
import com.xuan.mysingle.core.support.ListPager;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/28
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final static String CACHE_NAME = "application_cache";

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    @Cacheable(value = CACHE_NAME, key = "#root.methodName", unless = "#result==null || #result.size()==0")
    public List<Application> getApplications() {
        return applicationRepository.getAll();
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result==null")
    public Application getApplicationById(String id) {
        List<Application> applicationList = getApplications();
        Optional<Application> application = applicationList.stream().filter(app -> app.getId().equals(id)).findAny();
        if (application.isPresent()) {
            return application.get();
        }
        return null;
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public boolean addApplication(Application application) {
        return applicationRepository.add(application) > 0;
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public boolean updateApplication(Application application) {
        return applicationRepository.update(application) > 0;
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public boolean deleteApplication(String id) {
        return applicationRepository.delete(id) > 0;
    }

    @Override
    public List<Application> getApplicationByParam(String name, Pager pager) {
        //传统写法
        // Map<String, Object> params = new HashMap<String, Object>();
        // params.put("name", name);
        //params.put("start", pager.getStart());
        //params.put("length", pager.getPageSize());
        //pager.setTotalRow(applicationRepository.getCountByPage(params));
        //return applicationRepository.getByPage(params);
        List<Application> applicationList = getApplications();
        List<Application> filterApp = null;
        if (StringUtils.isEmpty(name)) {
            filterApp = applicationList;
        } else {
            filterApp = applicationList.stream().filter(x -> x.getName() != null && x.getName().contains(name)).collect(Collectors.toList());
        }
        return ListPager.getPage(filterApp, pager);
    }

    @Override
    public String getNameById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Application application = getApplicationById(id);
        if(application !=null){
            return application.getName();
        }
        return null;
    }

    @Override
    @Cacheable(value=CACHE_NAME,key="#ssoId",unless = "#result==null")
    public Application getApplicationBySsoId(String ssoId) {
        List<Application> applications =getApplications();
        Optional<Application> appOpt= applications.stream().filter(app->app.getSsoId()!=null && app.getSsoId().equals(ssoId)).findFirst();
        if(appOpt.isPresent()){
            return appOpt.get();
        }
        return null;
    }
}
