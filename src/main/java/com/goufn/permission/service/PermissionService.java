package com.goufn.permission.service;

import java.util.Set;

public interface PermissionService {

    /**
     * 根据用户id查询资源集合
     *
     * @param userId
     * @return set
     */
    Set<String> findPermsByUserId(Integer userId);
}
