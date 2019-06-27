package com.goufn.permission.service;

import com.goufn.permission.model.SysRole;

import java.util.Set;

public interface RoleService {

    Set<String> findRoleByUserId(long userId);

    SysRole findByName(String roleName);

    void createRole(SysRole sysRole);

}
