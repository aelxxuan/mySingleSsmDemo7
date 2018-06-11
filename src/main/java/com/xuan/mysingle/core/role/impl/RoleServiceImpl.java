/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.role.impl;

import com.xuan.mysingle.common.role.Role;
import com.xuan.mysingle.common.role.support.RoleRepository;
import com.xuan.mysingle.console.support.Pager;
import com.xuan.mysingle.core.role.RoleServcie;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/8
 */
@Service
public class RoleServiceImpl implements RoleServcie {
    private static final String CHCHE_NAME = "role_cache";
    @Autowired
    RoleRepository roleRepository;

    @Override
    @Cacheable(value = CHCHE_NAME, key = "#root.methodName", unless = "#result==null || #result.size()==0")
    public List<Role> getAllRoles() {
        return roleRepository.getAll();
    }

    @Override
    @CacheEvict(value = CHCHE_NAME, allEntries = true)
    public boolean addRole(Role role) {
        return roleRepository.add(role) > 0;
    }

    @Override
    @CacheEvict(value = CHCHE_NAME, allEntries = true)
    public boolean updateRole(Role role) {
        return roleRepository.update(role) > 0;
    }

    @Override
    @CacheEvict(value = CHCHE_NAME, allEntries = true) //清空所有的缓存
    public boolean deleteByIds(String ids) {
        String[] arr = ids.split(",");
        List<String> idList  = Stream.of(arr).filter(x -> !x.trim().isEmpty()).collect(Collectors.toList());
        //Arrays.asList(arr);
        return roleRepository.delete(idList) == idList.size();
    }

    @Override
    @Cacheable(value = CHCHE_NAME, key = "#id.toLowerCase()", unless = "#result==null")
    public Role getRoleById(String id) {
        return roleRepository.getById(id);
    }

    @Override
    @Cacheable(value = CHCHE_NAME, key = "#id", unless = "#result==null")
    public String getRoleNameById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Role role = roleRepository.getById(id);
        if (role == null) {
            return null;
        }
        return role.getName();
    }

    @Override
    public List<Role> getRoleByPage(String name, Pager pager) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("start", pager.getStart());
        map.put("length", pager.getPageSize());
        pager.setTotalRow(roleRepository.getCountByPage(map));
        return roleRepository.getByPage(map);
    }
}
