package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.common.result.ResultUtil;
import com.goufn.permission.entity.SysUser;
import com.goufn.permission.jwt.JWTToken;
import com.goufn.permission.jwt.JWTUtil;
import com.goufn.permission.jwt.PermissionProperties;
import com.goufn.permission.service.UserService;
import com.goufn.permission.utils.DateUtil;
import com.goufn.permission.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/user/login")
    public CommonResult login(@RequestBody SysUser requestUser) {
        log.info("[进入登录方法....]");
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        SysUser user = userService.findByName(username);
        if (user == null) {
            return ResultUtil.error("用户名不存在");
        }
        String turePw = PasswordUtil.encryptPassword(password, user.getSalt());

        if (!StringUtils.equals(user.getPassword(), turePw)) {
            return ResultUtil.error("密码错误");
        }
        // 更新用户登录时间
        this.userService.updateLoginTime(user);
        // 保存登录记录
//        LoginLog loginLog = new LoginLog();
//        loginLog.setUserId(user.getId());
//        this.loginLogService.saveLoginTime(loginLog);
        Map<String, Object> map = new HashMap<>();
        String tokenStr = JWTUtil.sign(username, password);
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(tokenStr, expireTimeStr);;
        map.put("token", jwtToken.getToken());
        log.info("[登录成功]-[{}]", username);
        return ResultUtil.success("登录成功", map);
    }
}
