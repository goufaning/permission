package com.goufn.permission.service;

import com.goufn.permission.entity.SysRole;

import java.util.Set;

public interface RoleService {

    Set<String> findRoleByUserId(int userId);

    SysRole findByName(String roleName);

    void createRole(SysRole sysRole);

}
