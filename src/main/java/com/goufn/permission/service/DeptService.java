package com.goufn.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.page.PageResult;
import com.goufn.permission.model.SysDept;

import java.util.List;

public interface DeptService extends IService<SysDept> {

    List<SysDept> findTree(String name);

    PageResult findPage(PageRequest pageRequest);

    int delete(List<SysDept> records);
}
