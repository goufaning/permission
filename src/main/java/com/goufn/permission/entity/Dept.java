package com.goufn.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class Dept implements Serializable {

    private static final long serialVersionUID = 1;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private int parentId;

    private String name;

    private Double orderNum;

    private Date createTime;

    private Date modifyTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}
