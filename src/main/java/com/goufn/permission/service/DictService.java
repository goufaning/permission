package com.goufn.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.page.PageResult;
import com.goufn.permission.model.SysDict;

import java.util.List;

public interface DictService extends IService<SysDict> {

    void delete(List<SysDict> records);

    PageResult findPage(PageRequest pageRequest);

    List<SysDict> findByLable(String lable);
}
