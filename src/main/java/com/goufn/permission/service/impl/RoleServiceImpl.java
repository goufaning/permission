package com.goufn.permission.service.impl;

import com.goufn.permission.mapper.RoleMapper;
import com.goufn.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Set<String> findRoleByUserId(int userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
