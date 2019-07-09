package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(ShiroException.class)
    public CommonResult handle401() {
        return CommonResult.unauthorized(null);
    }
}
