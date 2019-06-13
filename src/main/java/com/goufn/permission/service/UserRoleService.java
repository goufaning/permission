package com.goufn.permission.service;

public interface UserRoleService {

    /***
     * 添加用户角色
     * @param userId
     * @param roleIds
     */
    void addUserRole(Integer userId, String roleIds);
}
