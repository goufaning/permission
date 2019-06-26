package com.goufn.permission.common.result;


import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class ResultUtil {

    public static CommonResult result(int code, String message, Object data) {
        return new CommonResult<>(code, message, data);
    }

    public static CommonResult error(String message) {
        return result(ResultCode.FAILED.getCode(), message, null);
    }

    public static CommonResult success(String message) {
        return result(ResultCode.SUCCESS.getCode(), message, null);

    }

    public static CommonResult success(Object data) {
        return CommonResult.success(data);

    }

    public static CommonResult success(String message, Object data) {
        return result(ResultCode.SUCCESS.getCode(), message, data);
    }
}
