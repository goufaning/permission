package com.goufn.permission.entity;

import lombok.Data;


@Data
public class Role {
    private int id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    /** 用户状态：1有效2删除 **/
    private Integer status;

}
