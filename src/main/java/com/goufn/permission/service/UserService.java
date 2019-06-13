package com.goufn.permission.service;


import com.goufn.permission.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 获取指定用户ID对应的用户账户信息
     * @param userID 用户ID
     * @return 返回用户账户信息
     */
    User getUserInfo(Integer userID);

    /**
     * 获取指定 userName 对应的用户账户信息
     * @param userName 用户名
     * @return 返回用户账户信息
     */
    User getUserInfo(String userName);

    /**
     * 获取所有用户账户信息
     * @return 返回所有的用户账户信息
     */
    List<User> getAllUserInfo();

    /**
     * 更新用户的账户信息
     * @param user 用户账户信息
     */
    void updateUserInfo(User user);

    /**
     * 删除指定 userID 的用户账户信息
     * @param userID 指定的用户ID
     */
    void deleteUserInfo(Integer userID);

    /**
     * 添加一条用户账户信息
     * @param user 需要添加的用户账户信息
     */
    boolean insertUserInfo(User user);

    /**
     * 获取用户的权限角色
     * @param userID 用户 ID
     * @return 返回一个保存有用户角色的 Set，若该用户没有任何角色，则返回一个不包含任何元素的 Set
     */
    Set<String> getUserRoles(Integer userID);
}
