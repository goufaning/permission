package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.entity.SysRolePermission;
import com.goufn.permission.mapper.RolePermissionMapper;
import com.goufn.permission.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, SysRolePermission> implements RolePermissionService {
    @Override
    public void deletePermissionByRoleId(List<Integer> roleIds) {
        for (Integer roleId : roleIds) {
            baseMapper.delete(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId));
        }
    }

    @Override
    public void deleteByPermissionId(List<Integer> permissionIds) {
        for (Integer id : permissionIds) {
            baseMapper.delete(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getPermissionId, id));
        }
    }

    @Override
    public List<SysRolePermission> getRolePermissionByRoleId(int roleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId));
    }
}
