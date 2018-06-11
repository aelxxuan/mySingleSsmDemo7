/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.role;

import com.xuan.mysingle.common.role.Role;
import com.xuan.mysingle.console.support.Pager;

import java.util.List;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/8
 */
public interface RoleServcie {
    /**
     * 获得所有角色
     * @return
     */
    List<Role> getAllRoles();
    boolean addRole(Role role);
    boolean updateRole(Role role);
    boolean deleteByIds(String ids);
    Role getRoleById(String id);

    /**
     * 获取角色名称，如果为空返回Null
     * @param id
     * @return
     */
    String getRoleNameById(String id);
    List<Role> getRoleByPage(String name,Pager pager);

}
