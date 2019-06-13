package com.goufn.permission.entity;

import lombok.Data;


@Data
public class Permission {

    private Integer id;//主键.
    private String name;//名称.
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Integer type; // 资源类型:0目录 1菜单 2按钮
    /** 用户状态：1有效2删除 **/
    private Integer status;
    private String description; // 权限描述
}
