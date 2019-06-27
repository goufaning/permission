package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.model.SysDept;
import com.goufn.permission.mapper.DeptMapper;
import com.goufn.permission.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, SysDept> implements DeptService {

}
