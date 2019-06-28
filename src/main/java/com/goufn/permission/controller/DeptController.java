package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.common.result.ResultUtil;
import com.goufn.permission.model.SysDept;
import com.goufn.permission.service.DeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @RequiresPermissions({"sys:dept:add", "sys:dept:edit"})
    @PostMapping(value="/save")
    public CommonResult save(@RequestBody SysDept record) {
        if (record == null) {
            return ResultUtil.error("表格不能为空");
        }
        if (record.getId() == 0) {
            deptService.save(record);
        } else {
            deptService.updateById(record);
        }
        return ResultUtil.success(1);
    }

    @RequiresPermissions("sys:dept:delete")
    @PostMapping(value="/delete")
    public CommonResult delete(@RequestBody List<SysDept> records) {
        return ResultUtil.success(deptService.delete(records));
    }

    @RequiresPermissions("hasAuthority('sys:dept:view')")
    @GetMapping(value="/findTree")
    public CommonResult findTree() {
        return ResultUtil.success(deptService.findTree());
    }

}
