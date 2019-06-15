package com.goufn.permission.entity;

import lombok.Data;


@Data
public class SysRolePermission {
    private int id;

    private int userId;

    private int permissionId;
}
