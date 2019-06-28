package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.common.result.ResultUtil;
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

    @PostMapping(value="/save")
    @RequiresPermissions({"sys:menu:add", "sys:menu:edit"})
    public CommonResult save(@RequestBody SysMenu record) {
        return ResultUtil.success(menuService.save(record));
    }

    @PostMapping(value="/delete")
    @RequiresPermissions("sys:menu:delete")
    public CommonResult delete(@RequestBody List<SysMenu> records) {
        for (SysMenu record : records) {
            menuService.removeById(record.getId());
        }
        return ResultUtil.success(records.size());
    }

    @GetMapping("/findNavTree")
    @RequiresPermissions("sys:menu:view")
    public CommonResult findNavTree(@RequestParam String username) {
        List<SysMenu> menuList = menuService.findTree(username, 1);
        return ResultUtil.success(menuList);
    }

    @GetMapping("/findMenuTree")
    @RequiresPermissions("sys:menu:view")
    public CommonResult findMenuTree() {
        List<SysMenu> menuList = menuService.findTree(null, 0);
        return ResultUtil.success(menuList);
    }

}
