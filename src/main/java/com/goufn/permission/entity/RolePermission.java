package com.goufn.permission.entity;

import lombok.Data;


@Data
public class RolePermission {
    private int id;

    private int userId;

    private int permissionId;
}
