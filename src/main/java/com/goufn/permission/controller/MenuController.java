package com.goufn.permission.controller;

import com.goufn.permission.annotation.Log;
import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.model.SysMenu;
import com.goufn.permission.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Log("新增/修改菜单")
    @PostMapping(value="/save")
    @RequiresPermissions({"sys:menu:add", "sys:menu:edit"})
    public CommonResult save(@RequestBody SysMenu record) {
        if (record.getParentId() == null) {
            record.setParentId(0L);
        }
        menuService.saveOrUpdate(record);
        return CommonResult.success();
    }

    @Log("删除菜单/按钮")
    @PostMapping(value="/delete")
    @RequiresPermissions("sys:menu:delete")
    public CommonResult delete(@RequestBody List<SysMenu> records) {
        for (SysMenu record : records) {
            menuService.removeById(record.getId());
        }
        return CommonResult.success(records.size());
    }

    @GetMapping("/findNavTree")
    @RequiresPermissions("sys:menu:view")
    public CommonResult findNavTree(@RequestParam String username) {
        List<SysMenu> menuList = menuService.findTree(username, 1, null);
        return CommonResult.success(menuList);
    }

    @GetMapping("/findMenuTree")
    @RequiresPermissions("sys:menu:view")
    public CommonResult findMenuTree(@RequestParam String name) {
        List<SysMenu> menuList = menuService.findTree(null, 0, name);
        return CommonResult.success(menuList);
    }

}
