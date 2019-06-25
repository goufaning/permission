package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.entity.SysUserRole;
import com.goufn.permission.mapper.UserRoleMapper;
import com.goufn.permission.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, SysUserRole> implements UserRoleService {
    @Override
    public void deleteByRoleId(int roleId) {
        baseMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
    }

    @Override
    public void deleteByUserId(int userId) {
        baseMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
    }

    @Override
    public List<SysUserRole> findUserIdsByRoleId(int roleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
    }
}
