package com.goufn.permission.service.impl;

import com.goufn.permission.mapper.PermissionMapper;
import com.goufn.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper mapper;
    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return mapper.findPermsByUserId(userId);
    }
}
