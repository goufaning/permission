package com.goufn.permission.model;

import lombok.Data;


@Data
public class SysRole extends BaseModel {

    private String name;

    private String remark;

    private Byte delFlag;


}
