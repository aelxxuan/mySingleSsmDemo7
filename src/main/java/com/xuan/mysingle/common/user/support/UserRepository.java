package com.xuan.mysingle.common.user.support;

import com.xuan.mysingle.common.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/19
 */
@Repository
public interface UserRepository {
    User getById(int userId);
    List<User> getByPage(Map map);
    int getCountByPage(Map map);
    int add(User user);
    int update(User user);
    int delete(int userId);
    User getByPhone(String phone);
    User getByLoginName(String userName);

}
