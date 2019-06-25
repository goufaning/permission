package com.goufn.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goufn.permission.entity.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {

    List<Dept> deptList();

    void createDept(Dept dept);

    void updateDept(Dept dept);

    void deleteDepts(List<Integer> ids);
}
