package com.goufn.permission.controller;

import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.model.SysUser;
import com.goufn.permission.service.UserService;
import com.goufn.permission.utils.PasswordUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresPermissions({"sys:user:add", "sys:user:edit"})
    @PostMapping(value="/save")
    public CommonResult save(@RequestBody SysUser record) {
        SysUser user = userService.findById(record.getId());
        if(user != null) {
            if("admin".equalsIgnoreCase(user.getName())) {
                return CommonResult.error("超级管理员不允许修改!");
            }
        }
        if(record.getPassword() != null) {
            if(user == null) {
                // 新增用户
                if(userService.findByName(record.getName()) != null) {
                    return CommonResult.error("用户名已存在!");
                }
                PasswordUtil.encryptPassword(record);
            } else {
                // 修改用户, 且修改了密码
                if(!record.getPassword().equals(user.getPassword())) {
                    PasswordUtil.encryptPassword(record);
                }
            }
        }
        return CommonResult.success(userService.save(record));
    }

    @RequiresPermissions("sys:user:delete")
    @PostMapping(value="/delete")
    public CommonResult delete(@RequestBody List<SysUser> records) {
        for(SysUser record : records) {
            SysUser sysUser = userService.findById(record.getId());
            if(sysUser != null && "admin".equalsIgnoreCase(sysUser.getName())) {
                return CommonResult.error("超级管理员不允许删除!");
            }
        }
        return CommonResult.success(userService.delete(records));
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(value="/findByName")
    public CommonResult findByUserName(@RequestParam String name) {
        return CommonResult.success(userService.findByName(name));
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(value="/findPermissions")
    public CommonResult findPermissions(@RequestParam String name) {
        return CommonResult.success(userService.findPermissions(name));
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(value="/findUserRoles")
    public CommonResult findUserRoles(@RequestParam Long userId) {
        return CommonResult.success(userService.findUserRoles(userId));
    }

    @RequiresPermissions("sys:user:view")
    @PostMapping(value="/findPage")
    public CommonResult findPage(@RequestBody PageRequest pageRequest) {
        return CommonResult.success(userService.findPage(pageRequest));
    }
}
