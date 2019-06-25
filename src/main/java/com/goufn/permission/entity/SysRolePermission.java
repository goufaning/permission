package com.goufn.permission.entity;

import lombok.Data;


@Data
public class SysRolePermission {

    /** 角色id  */
    private int roleId;

    /** 权限id */
    private int permissionId;
}
