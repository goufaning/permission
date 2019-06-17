package com.goufn.permission.service;

import com.goufn.permission.common.vo.MenuVo;
import com.goufn.permission.entity.SysPermission;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    /**
     * 根据用户id查询资源集合
     *
     * @param userId
     * @return set
     */
    Set<String> findPermsByUserId(Integer userId);

    List<SysPermission> getChildList(int id);

    List<MenuVo> getMenuList(int userId);
}
