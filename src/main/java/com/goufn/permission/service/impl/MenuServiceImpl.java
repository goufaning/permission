package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.mapper.RoleMenuMapper;
import com.goufn.permission.mapper.UserMapper;
import com.goufn.permission.mapper.UserRoleMapper;
import com.goufn.permission.model.SysDept;
import com.goufn.permission.model.SysMenu;
import com.goufn.permission.mapper.MenuMapper;
import com.goufn.permission.model.SysUser;
import com.goufn.permission.model.SysUserRole;
import com.goufn.permission.service.MenuService;
import com.goufn.permission.service.RoleService;
import com.goufn.permission.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, SysMenu> implements MenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleService roleService;


    @Override
    public Set<String> findPermsByUserId(long userId) {
        Set<String> perms = new HashSet<>();
        SysUser user = userService.findById(userId);
        for (SysMenu sysMenu : findByUser(user.getName(), null)) {
            perms.add(sysMenu.getPerms());
        }
        return perms;
    }

    @Override
    public List<SysMenu> findByUser(String userName, Wrapper<SysMenu> queryWrapper) {
        if (userName == null || "".equals(userName) || "admin".equals(userName)) {
            return this.list(queryWrapper);
        }
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getName, userName));
        if (sysUser == null) {
            return new ArrayList<>();
        }
        List<SysUserRole> sysUserRoles = userRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, sysUser.getId()));
        List<Long> roldIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<SysMenu> result = new ArrayList<>();
        for (Long roldId : roldIds) {
            result.addAll(roleService.findRoleMenus(roldId));
        }
        return result;
    }

    @Override
    public List<SysMenu> findTree(String userName, int menuType, String name) {
        List<SysMenu> resultMenus = new ArrayList<>();
        // 是否是匹配name查询
        boolean isSearch = false;
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(SysMenu::getName, name);
            isSearch = true;
        }
        List<SysMenu> findMenus = findByUser(userName, wrapper);
        if (isSearch) {
            // 子节点匹配但父节点没匹配 把父节点加入
            Set<SysMenu> menuSet = new HashSet<>();
            for (SysMenu menu : findMenus) {
                SysMenu tempMenu = menu;
                while (tempMenu != null && tempMenu.getParentId() != null && tempMenu.getParentId() != 0) {
                    SysMenu parent = getById(tempMenu.getParentId());
                    // 如果没有加入父节点，加入
                    if (parent != null && !findMenus.contains(parent)) {
                        menuSet.add(parent);
                    }
                    tempMenu = parent;
                }
            }
            findMenus.addAll(menuSet);
        }
        // 为顶级父节点设置level=0,并加入到结果集中
        for (SysMenu menu : findMenus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (!exists(resultMenus, menu)) {
                    resultMenus.add(menu);
                }
            }
        }
        // 升序排序
        resultMenus.sort(Comparator.comparing(SysMenu::getOrderNum));
        findChildren(resultMenus, findMenus, menuType);
        return resultMenus;
    }

    /**
     * 从chooseMenus挑选parentMenus中菜单的子菜单
     * @param parentMenus
     * @param chooseMenus
     * @param menuType
     */
    private void findChildren(List<SysMenu> parentMenus, List<SysMenu> chooseMenus, int menuType) {
        for (SysMenu parentMenu : parentMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : chooseMenus) {
                if(menu.getType() != menuType && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (parentMenu.getId() != null && parentMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(parentMenu.getName());
                    menu.setLevel(parentMenu.getLevel() + 1);
                    if(!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            parentMenu.setChildren(children);
            children.sort(Comparator.comparing(SysMenu::getOrderNum));
            findChildren(children, chooseMenus, menuType);
        }
    }

    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        for(SysMenu menu : sysMenus) {
            if(menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }
}
