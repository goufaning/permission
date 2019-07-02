package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.model.SysUser;
import com.goufn.permission.jwt.JWTToken;
import com.goufn.permission.jwt.JWTUtil;
import com.goufn.permission.jwt.PermissionProperties;
import com.goufn.permission.service.UserService;
import com.goufn.permission.utils.DateUtil;
import com.goufn.permission.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



@RestController
@Slf4j
public class LoginController {

    @Autowired
    private PermissionProperties properties;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody SysUser requestUser) {
        log.info("[进入登录方法....]");
        String username = requestUser.getName();
        String password = requestUser.getPassword();
        // 获取当前的用户的 Subject，shiro
        SysUser user = userService.findByName(username);
        if (user == null) {
            return CommonResult.error("用户名不存在");
        }
        String passwdWithSalt = PasswordUtil.encryptPassword(password, user.getSalt());
        if (!StringUtils.equals(user.getPassword(), passwdWithSalt)) {
            return CommonResult.error("密码错误");
        }
        userService.updateLoginTime(user);
        String token = JWTUtil.sign(username, passwdWithSalt);
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);
        Map<String, Object> map = new HashMap<>();
        map.put("token", jwtToken.getToken());
        log.info("[登录成功]-[{}]", username);
        return CommonResult.success("登录成功", map);
    }

    @GetMapping("/login")
    public CommonResult logout() {
        return CommonResult.success("登出成功");
    }
}
