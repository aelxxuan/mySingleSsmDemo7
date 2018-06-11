package com.xuan.mysingle.common.role.support;

import com.xuan.mysingle.common.role.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/6/8
 */
@Repository
public interface RoleRepository {
    List<Role> getAll();
    int add(Role role);
    int update(Role role);
    int delete(List<String> ids);
    Role getById(String id);
    List<Role> getByPage(Map map);
    int getCountByPage(Map map);
}
