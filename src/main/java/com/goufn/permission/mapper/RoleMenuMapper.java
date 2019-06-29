package com.goufn.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goufn.permission.model.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
