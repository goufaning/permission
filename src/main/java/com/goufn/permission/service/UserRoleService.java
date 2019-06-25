package com.goufn.permission.service;

import com.goufn.permission.entity.SysUserRole;

import java.util.List;

public interface UserRoleService {

    void deleteByRoleId(int roleId);

    void deleteByUserId(int roleId);

    List<SysUserRole> findUserIdsByRoleId(int roleId);
}
