package com.goufn.permission.controller;

import com.goufn.permission.common.result.CommonResult;
import com.goufn.permission.common.result.ResultUtil;
import com.goufn.permission.common.vo.MenuVo;
import com.goufn.permission.entity.SysUser;
import com.goufn.permission.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/leftNav")
    public CommonResult leftNav() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return ResultUtil.error("未登录");
        }
        List<MenuVo> menuVoList = permissionService.getMenuList(user.getId());
        return ResultUtil.success(menuVoList);
    }

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        File sourceFile =  resource.getFile();
        InputStream is = new FileInputStream(sourceFile);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
}
