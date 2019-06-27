package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.model.SysMenu;
import com.goufn.permission.mapper.MenuMapper;
import com.goufn.permission.model.SysUser;
import com.goufn.permission.service.MenuService;
import com.goufn.permission.service.UserService;
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
    public boolean save(SysMenu entity) {
        if (entity.getParentId() == null) {
            entity.setParentId(0L);
        }
        return super.save(entity);
    }

    @Override
    public Set<String> findPermsByUserId(long userId) {
        Set<String> perms = new HashSet<>();
        SysUser user = userService.findById(userId);
        for (SysMenu sysMenu : findByUser(user.getName())) {
            perms.add(sysMenu.getPerms());
        }
        return perms;
    }

    @Override
    public List<SysMenu> findByUser(String userName) {
        if (userName == null || "".equals(userName) || "admin".equals(userName)) {
            return this.baseMapper.selectList(null);
        }
        return baseMapper.findByUserName(userName);
    }

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> permissions = findByUser(userName);
        for (SysMenu permission : permissions) {
            if (permission.getParentId() == null || permission.getParentId() == 0) {
                permission.setLevel(0);
                if (!exists(sysMenus, permission)) {
                    sysMenus.add(permission);
                }
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, permissions, menuType);
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
