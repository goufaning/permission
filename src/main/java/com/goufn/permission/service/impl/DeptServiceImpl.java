package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.entity.Dept;
import com.goufn.permission.mapper.DeptMapper;
import com.goufn.permission.service.DeptService;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Dept> deptList() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public void createDept(Dept dept) {
        Integer parentId = dept.getParentId();
        if (parentId == null) {
            dept.setParentId(0);
        }
        dept.setCreateTime(new Date());
        this.save(dept);
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setModifyTime(new Date());
        this.baseMapper.updateById(dept);
    }

    @Override
    public void deleteDepts(List<Integer> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }
}
