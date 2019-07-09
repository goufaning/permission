package com.goufn.permission.aspect;

import com.alibaba.fastjson.JSONObject;
import com.goufn.permission.annotation.Log;
import com.goufn.permission.jwt.JWTUtil;
import com.goufn.permission.model.SysLog;
import com.goufn.permission.service.SysLogService;
import com.goufn.permission.utils.HttpUtils;
import com.goufn.permission.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private SysLogService logService;

    @Pointcut("@annotation(com.goufn.permission.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 获取request
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        String ip = IPUtils.getIpAddr(request);
        // 执行方法
        result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = "";
        if (StringUtils.isNotBlank(token)) {
            username = JWTUtil.getUsername(token);
        }
        SysLog sysLog = new SysLog();
        sysLog.setUserName(username);
        sysLog.setCreateBy(username);
        sysLog.setCreateTime(new Date());
        sysLog.setLastUpdateBy(username);
        sysLog.setLastUpdateTime(new Date());
        sysLog.setIp(ip);
        sysLog.setTime(time);
        String className = point.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = point.getArgs();
        try{
            String params = JSONObject.toJSONString(args[0]);
            if(params.length() > 200) {
                params = params.substring(0, 200) + "...";
            }
            sysLog.setParams(params);
        } catch (Exception e){
        }
        logService.save(sysLog);
        return result;
    }

}
