package com.goufn.permission.service;


import com.goufn.permission.entity.SysUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 获取指定用户ID对应的用户账户信息
     * @param userId 用户ID
     * @return 返回用户账户信息
     */
    SysUser findById(Integer userId);

    /**
     * 获取指定 userName 对应的用户账户信息
     * @param userName 用户名
     * @return 返回用户账户信息
     */
    SysUser findByName(String userName);

    /**
     * 获取所有用户账户信息
     * @return 返回所有的用户账户信息
     */
    List<SysUser> listUser();


    void updateLoginTime(SysUser user);

    /**
     * 删除指定 userID 的用户账户信息
     * @param userID 指定的用户ID
     */
    void deleteUserInfo(Integer userID);

    /**
     * 添加一条用户账户信息
     * @param user 需要添加的用户账户信息
     */
    void createUser(SysUser user);

}
