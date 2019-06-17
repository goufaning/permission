package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goufn.permission.common.vo.MenuVo;
import com.goufn.permission.entity.SysPermission;
import com.goufn.permission.mapper.PermissionMapper;
import com.goufn.permission.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper mapper;
    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return mapper.findPermsByUserId(userId);
    }

    @Override
    public List<SysPermission> getChildList(int id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        return mapper.selectList(wrapper);
    }

    @Override
    public List<MenuVo> getMenuList(int userId) {
        List<MenuVo> menuVoList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type", 1);
        List<SysPermission> permissions = mapper.selectList(wrapper);
        for (SysPermission permission : permissions) {
            if (permission.getParentId() == 0) {
                MenuVo menuVo = new MenuVo(permission);
                List<SysPermission> childs = getChildList(permission.getId());
                if (childs != null && childs.size() > 0) {
                    List<MenuVo> menuChild = new ArrayList<>();
                    for (SysPermission p : childs) {
                        MenuVo child  = new MenuVo(p);
                        menuChild.add(child);
                    }
                    menuVo.setChildren(menuChild);
                }
                menuVoList.add(menuVo);
            }
        }
        return menuVoList;
    }
}
