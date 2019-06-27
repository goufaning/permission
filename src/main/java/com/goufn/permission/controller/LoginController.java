package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.common.result.ResultUtil;
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
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取当前的用户的 Subject，shiro
        Subject currentUser = SecurityUtils.getSubject();
        // 判断用户是否已经登陆
        // 执行登陆操作
        try {
            //会调用realms/UserAuthorizingRealm中的doGetAuthenticationInfo方法
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            return ResultUtil.error("用户名不存在");
        } catch (IncorrectCredentialsException ice) {
            return ResultUtil.error("密码错误");
        } catch (LockedAccountException lae) {
            return ResultUtil.error("LockedAccountException");
        } catch (ExcessiveAttemptsException eae) {
            return ResultUtil.error("ExcessiveAttemptsException");
        } catch (AuthenticationException ae) {
            return ResultUtil.error("AuthenticationException");
        } catch (Exception e) {
            return ResultUtil.error("未知错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", "123");
        log.info("[登录成功]-[{}]", username);
        return ResultUtil.success("登录成功", map);
    }
}
