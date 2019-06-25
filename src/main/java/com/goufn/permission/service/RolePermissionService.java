package com.goufn.permission.service;

import com.goufn.permission.entity.SysRolePermission;

import java.util.List;

public interface RolePermissionService {

    void deletePermissionByRoleId(List<Integer> roleIds);

    void deleteByPermissionId(List<Integer> menuIds);

    List<SysRolePermission> getRolePermissionByRoleId(int roleId);
}
