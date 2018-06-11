/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.user.impl;

import com.xuan.mysingle.common.user.User;
import com.xuan.mysingle.common.user.support.UserRepository;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${mysingle.uploadFile.path}")
    private String uploadFileUrl;

    public static final String USER_CACHE = "userCache";

    @Override
    @Cacheable(value = USER_CACHE, key = "#userId", unless = "#result ==null")
    //@Cacheable(value = "userCache") //key为userid的字符串显示，比如userId为157，key为："157"
    public User getById(int userId) {
        return userRepository.getById(userId);
    }

    @Override
    public List<User> getUserByPage(String name, Pager pager) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("pageIndex", pager.getStart());
        params.put("pageSize", pager.getPageSize());
        int totalRow = userRepository.getCountByPage(params);
        pager.setTotalRow(totalRow);
        return userRepository.getByPage(params);
    }

    @Override
    @CacheEvict(value = USER_CACHE, allEntries = true, beforeInvocation = true)
    public boolean addUser(User user) {
        Date date = new Date();
        user.setAddTime(date);
        user.setUpdateTime(date);
        return userRepository.add(user) > 0;
    }

    //@CacheEvict(value = USER_CACHE, key = "#userId",allEntries = true) //这里放key是没有意思的
    //@CacheEvict(value = USER_CACHE)
    @CacheEvict(value = USER_CACHE, key = "#userId",condition = "#userId !=null")
    @Override
    public boolean deleteUser(int userId) {
        return userRepository.delete(userId) > 0;
    }

    @Override
    @CacheEvict(value = USER_CACHE,key = "#user.userId")
    public boolean updateUser(User user) {
        user.setUpdateTime(new Date());
        return userRepository.update(user) > 0;
    }

    @Override
    public List<User> getUserByParam(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        return userRepository.getByPage(params);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.getByPhone(phone);
    }

    @Override
    public String getUploadPath() {
        return uploadFileUrl;
    }

    @Override
    public User getUserByLoginName(String userName) {
        return userRepository.getByLoginName(userName);
    }
}
