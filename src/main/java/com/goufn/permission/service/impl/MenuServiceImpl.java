package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.model.SysDept;
import com.goufn.permission.model.SysMenu;
import com.goufn.permission.mapper.MenuMapper;
import com.goufn.permission.model.SysUser;
import com.goufn.permission.service.MenuService;
import com.goufn.permission.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, SysMenu> implements MenuService {

    @Autowired
    private UserService userService;


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
        return baseMapper.findByUserName(userName);
    }

    @Override
    public List<SysMenu> findTree(String userName, int menuType, String name) {
        List<SysMenu> sysMenus = new ArrayList<>();
        boolean isSearch = false;
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(SysMenu::getName, name);
            isSearch = true;
        }
        List<SysMenu> menus = findByUser(userName, wrapper);
        if (isSearch) {
            // 子节点匹配但父节点没匹配 把父节点加入
            Set<SysMenu> menuSet = new HashSet<>();
            for (SysMenu menu : menus) {
                SysMenu tempDept = menu;
                while (tempDept != null && tempDept.getParentId() != null && tempDept.getParentId() != 0) {
                    SysMenu parent = getById(tempDept.getParentId());
                    if (parent != null && !menus.contains(parent)) {
                        menuSet.add(parent);
                    }
                    tempDept = parent;
                }
            }
            menus.addAll(menuSet);
        }
        for (SysMenu permission : menus) {
            if (permission.getParentId() == null || permission.getParentId() == 0) {
                permission.setLevel(0);
                if (!exists(sysMenus, permission)) {
                    sysMenus.add(permission);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        return sysMenus;
    }

    private void findChildren(List<SysMenu> sysMenus, List<SysMenu> permissions, int menuType) {
        for (SysMenu permission : sysMenus) {
            List<SysMenu> children = new ArrayList<>();
            for (SysMenu menu : permissions) {
                if(menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (permission.getId() != null && permission.getId().equals(menu.getParentId())) {
                    menu.setParentName(permission.getName());
                    menu.setLevel(permission.getLevel() + 1);
                    if(!exists(children, menu)) {
                        children.add(menu);
                    }
                }
            }
            permission.setChildren(children);
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            findChildren(children, permissions, menuType);
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
