package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.entity.SysRole;
import com.goufn.permission.mapper.RoleMapper;
import com.goufn.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {

    @Override
    public Set<String> findRoleByUserId(int userId) {
        return baseMapper.findRoleByUserId(userId);
    }

    @Override
    public SysRole findByName(String roleName) {
        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.eq("name", roleName);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void createRole(SysRole sysRole) {

    }
}
