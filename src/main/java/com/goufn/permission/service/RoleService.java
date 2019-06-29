package com.goufn.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.page.PageResult;
import com.goufn.permission.model.SysMenu;
import com.goufn.permission.model.SysRole;
import com.goufn.permission.model.SysRoleMenu;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<SysRole> {

    Set<String> findRoleByUserId(long userId);

    void delete(List<SysRole> roles);

    PageResult findPage(PageRequest pageRequest);

    List<SysMenu> findRoleMenus(long roleId);

    void saveRoleMenus(List<SysRoleMenu> sysRoleMenus);

    List<SysRole> findByName(String name);

}
