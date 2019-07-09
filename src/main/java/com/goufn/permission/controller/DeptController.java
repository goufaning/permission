package com.goufn.permission.controller;

import com.goufn.permission.annotation.Log;
import com.goufn.permission.common.result.CommonResult;
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

    @Log("新增/修改部门")
    @RequiresPermissions({"sys:dept:add", "sys:dept:edit"})
    @PostMapping(value="/save")
    public CommonResult save(@RequestBody SysDept record) {
        if (record == null) {
            return CommonResult.error("表格不能为空");
        }
        deptService.saveOrUpdate(record);
        return CommonResult.success();
    }

    @Log("删除部门")
    @RequiresPermissions("sys:dept:delete")
    @PostMapping(value="/delete")
    public CommonResult delete(@RequestBody List<SysDept> records) {
        return CommonResult.success(deptService.delete(records));
    }

    @RequiresPermissions("sys:dept:view")
    @GetMapping(value="/findTree")
    public CommonResult findTree(@RequestParam(required = false) String name) {
        return CommonResult.success(deptService.findTree(name));
    }

}
