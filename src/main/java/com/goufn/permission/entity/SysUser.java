package com.goufn.permission.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    private int id;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;
    /** 盐 **/
    private String salt;
    /** 邮箱 **/
    private String email;
    /** 联系方式 **/
    private String phone;
    /** 性别：1男2女0未知 **/
    private Integer sex;
    /** 用户状态：1有效2删除 **/
    private Integer status;
    /** 最后登陆时间 **/
    private Date lastLoginTime;
    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date updateTime;

    private transient int roleId;

}
