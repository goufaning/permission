package com.goufn.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goufn.permission.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
