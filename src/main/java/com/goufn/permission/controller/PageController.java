package com.goufn.permission.controller;

import com.goufn.permission.common.result.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class PageController {

    @RequestMapping("/page/{page}")
    public ModelAndView welcome(@PathVariable String page) {
        return ResultUtil.view("page/" + page);
    }


    @GetMapping(value = { "/login"})
    public ModelAndView loginView() {
        return ResultUtil.view("/page/login/login");
    }

    @GetMapping(value = {"/", "/index"})
    public ModelAndView indexView() {
        return ResultUtil.view("/index");
    }
}
