package com.goufn.permission.service;

import com.goufn.permission.entity.LoginLog;

public interface LoginLogService {

    void saveLoginTime(LoginLog loginLog);
}
