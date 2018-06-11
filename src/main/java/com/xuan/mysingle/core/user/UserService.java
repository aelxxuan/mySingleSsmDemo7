package com.xuan.mysingle.core.user;

import com.xuan.mysingle.common.user.User;
import com.xuan.mysingle.console.support.Pager;

import java.util.List;
import java.util.Map;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/4/19
 */
public interface UserService {
    /**
     * 根据Id获取用户
     * @param userId 用户ID
     * @return 用户
     */
    User getById(int userId);

    /**
     * 分页获取列表
     * @param name 姓名
     * @param pager 页面实体
     * @return 用户列表
     */
    List<User> getUserByPage(String name, Pager pager);

    /**
     * 添加用户
     *
     * @param user 要填的的用户实体
     * @return 布尔值
     */
    boolean addUser(User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    boolean deleteUser(int userId);

    /**
     * 修改用户
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 获取用户列表
     * @param name
     * @return
     */
    List<User> getUserByParam(String name);

    User getUserByPhone(String phone);

    String getUploadPath();

    User getUserByLoginName(String name);
}
