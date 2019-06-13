package com.goufn.permission.service;

import java.util.Set;

public interface RoleService {

    Set<String> findRoleByUserId(int userId);
}
