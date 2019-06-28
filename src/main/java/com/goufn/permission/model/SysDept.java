package com.goufn.permission.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysDept extends BaseModel {

    private String name;

    private Long parentId;

    private Integer orderNum;

    private Byte delFlag;
    @TableField(exist = false)
    private List<SysDept> children;

    @TableField(exist = false)
    private String parentName;
    @TableField(exist = false)
    private Integer level;

}
