package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.common.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@Slf4j
public class LoginController {

    @PostMapping("/login")
    public CommonResult login(HttpServletRequest request, @RequestParam("username") String username,
                              @RequestParam("password")String password,
                              @RequestParam(name = "rememberme", defaultValue = "false") Boolean rememberme) {
        log.info("[进入登录方法....]");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取当前的用户的 Subject，shiro
        Subject currentUser = SecurityUtils.getSubject();
        // 判断用户是否已经登陆
        // 执行登陆操作
        try {
            //会调用realms/UserAuthorizingRealm中的doGetAuthenticationInfo方法
            currentUser.login(token);
        } catch (Exception e) {
            token.clear();
            return ResultUtil.error("登录失败");
        }
        log.info("[登录成功]-[{}]", username);
        return ResultUtil.success("登录成功");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        // http://www.oschina.net/question/99751_91561  此处有坑，这里其实可用使用shiro自带的退出，不用你实现任何东西
        return ResultUtil.redirect("login");
    }


}
