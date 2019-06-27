package com.goufn.permission.service;

import com.goufn.permission.model.SysRoleMenu;

import java.util.List;

public interface RolePermissionService {

    void deletePermissionByRoleId(List<Integer> roleIds);

    void deleteByPermissionId(List<Integer> menuIds);

    List<SysRoleMenu> getRolePermissionByRoleId(int roleId);
}
