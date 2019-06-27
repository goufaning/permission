package com.goufn.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goufn.permission.model.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Mapper
public interface MenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id查询资源集合
     *
     * @param userId 状态
     * @return set
     */
    Set<String> findPermsByUserId(Integer userId);

    /**
     * 查询用户按钮
     * @param username
     * @return
     */
    List<SysMenu> findByUserName(String username);
}
