package com.goufn.permission.common.page;

import lombok.Data;

@Data
public class ColumnFilter {
    /**
     * 过滤列名
     */
    private String name;
    /**
     * 查询的值
     */
    private String value;
}
