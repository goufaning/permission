package com.goufn.permission.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
@Data
public class PageResult {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 分页数据
     */
    private List<?> content;

    public PageResult(IPage page) {
        this.setPageNum((int) page.getCurrent());
        this.setPageSize((int) page.getSize());
        this.setTotalSize(page.getTotal());
        this.setTotalPages((int) page.getPages());
        this.setContent(page.getRecords());
    }
}
