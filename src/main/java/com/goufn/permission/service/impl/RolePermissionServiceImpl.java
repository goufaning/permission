package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.model.SysRoleMenu;
import com.goufn.permission.mapper.RoleMenuMapper;
import com.goufn.permission.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RoleMenuMapper, SysRoleMenu> implements RolePermissionService {
    @Override
    public void deletePermissionByRoleId(List<Integer> roleIds) {
        for (Integer roleId : roleIds) {
            baseMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        }
    }

    @Override
    public void deleteByPermissionId(List<Integer> permissionIds) {
        for (Integer id : permissionIds) {
            baseMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, id));
        }
    }

    @Override
    public List<SysRoleMenu> getRolePermissionByRoleId(int roleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
    }
}
