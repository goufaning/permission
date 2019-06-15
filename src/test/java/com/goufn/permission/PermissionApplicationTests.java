package com.goufn.permission;

import com.goufn.permission.entity.SysUser;
import com.goufn.permission.mapper.UserMapper;
import com.goufn.permission.utils.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionApplicationTests {
    @Autowired
    private UserMapper mapper;

    @Test
    public void addUser() {
        SysUser user = new SysUser();

        user.setUsername("admin");
        user.setPassword("123456");
        PasswordUtil.encryptPassword(user);
        mapper.insert(user);
    }

    @Test
    public void contextLoads() {
    }

}
