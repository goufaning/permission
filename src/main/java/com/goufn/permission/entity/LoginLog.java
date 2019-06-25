package com.goufn.permission.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoginLog {
    /**
     * 用户 ID
     */
    private int userId;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录地点
     */
    private String location;

    private String ip;
}
