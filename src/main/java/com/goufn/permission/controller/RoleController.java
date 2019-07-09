package com.goufn.permission.controller;

import com.goufn.permission.annotation.Log;
import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.jwt.JWTUtil;
import com.goufn.permission.mapper.RoleMapper;
import com.goufn.permission.model.SysRole;
import com.goufn.permission.model.SysRoleMenu;
import com.goufn.permission.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;

    @Log("新增/修改角色")
    @RequiresPermissions({"sys:role:add", "sys:role:edit"})
    @PostMapping(value="/save")
    public CommonResult save(@RequestBody SysRole record) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(token);
        record.setLastUpdateTime(new Date());
        record.setLastUpdateBy(username);
        SysRole role = roleService.getById(record.getId());
        if(role != null) {
            if("admin".equalsIgnoreCase(role.getName())) {
                return CommonResult.error("超级管理员不允许修改!");
            }
        }
        // 新增角色
        if((record.getId() == null || record.getId() ==0)) {
            if (!roleService.findByName(record.getName()).isEmpty()) {
                return CommonResult.error("角色名已存在!");
            }
            record.setCreateTime(new Date());
            record.setCreateBy(username);
            roleService.save(record);
        } else {
            record.setLastUpdateTime(new Date());
            roleService.updateById(record);
        }
        return CommonResult.success();
    }

    @Log("删除角色")
    @RequiresPermissions("sys:role:delete")
    @PostMapping(value="/delete")
    public CommonResult delete(@RequestBody List<SysRole> records) {
        roleService.delete(records);
        return CommonResult.success();
    }

    @RequiresPermissions("sys:role:view")
    @PostMapping(value="/findPage")
    public CommonResult findPage(@RequestBody PageRequest pageRequest) {
        return CommonResult.success(roleService.findPage(pageRequest));
    }

    @RequiresPermissions("sys:role:view")
    @GetMapping(value="/findAll")
    public CommonResult findAll() {
        return CommonResult.success(roleService.list());
    }

    @RequiresPermissions("sys:role:view")
    @GetMapping(value="/findRoleMenus")
    public CommonResult findRoleMenus(@RequestParam Long roleId) {
        return CommonResult.success(roleService.findRoleMenus(roleId));
    }

    @Log("修改角色权限")
    @RequiresPermissions("sys:role:edit")
    @PostMapping(value="/saveRoleMenus")
    public CommonResult saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
        for(SysRoleMenu record:records) {
            SysRole sysRole = roleMapper.selectById(record.getRoleId());
            if("admin".equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return CommonResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
        }
        roleService.saveRoleMenus(records);
        return CommonResult.success();
    }
}
