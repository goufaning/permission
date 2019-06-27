package com.goufn.permission.model;

import lombok.Data;

import java.util.Date;
@Data
public class BaseModel {

    private Long id;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;
}