package com.goufn.permission.controller;

import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequiresPermissions("sys:log:view")
    @PostMapping(value="/findPage")
    public CommonResult findPage(@RequestBody PageRequest pageRequest) {
        return CommonResult.success(sysLogService.findPage(pageRequest));
    }
}
