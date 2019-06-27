package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.model.SysUser;
import com.goufn.permission.mapper.MenuMapper;
import com.goufn.permission.mapper.RoleMapper;
import com.goufn.permission.mapper.UserMapper;
import com.goufn.permission.service.RolePermissionService;
import com.goufn.permission.service.UserRoleService;
import com.goufn.permission.service.UserService;
import com.goufn.permission.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;


    @Override
    public SysUser findById(Long userId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getId, userId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public SysUser findByName(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getName, userName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<SysUser> listUser() {
        return baseMapper.selectList(null);
    }

    @Override
    public void updateLoginTime(SysUser user) {
        user.setLastUpdateTime(new Date());
        updateById(user);
    }

    @Override
    public void deleteUserInfo(Integer userID) {
        removeById(userID);
        this.userRoleService.deleteByUserId(userID);
    }

    @Override
    public void createUser(SysUser user) {
        user.setCreateTime(new Date());
        PasswordUtil.encryptPassword(user);
        save(user);
    }

}
