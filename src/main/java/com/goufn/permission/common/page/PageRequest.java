package com.goufn.permission.common.page;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页数量
     */
    private int pageSize = 10;

    private Map<String, ColumnFilter> columnFilters = new HashMap<String, ColumnFilter>();
}
