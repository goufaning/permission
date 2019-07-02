package com.goufn.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goufn.permission.common.page.PageRequest;
import com.goufn.permission.common.page.PageResult;
import com.goufn.permission.model.SysDept;
import com.goufn.permission.mapper.DeptMapper;
import com.goufn.permission.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, SysDept> implements DeptService {

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        Page<SysDept> page = new Page<>(pageNum, pageSize);
        IPage<SysDept> result = baseMapper.selectPage(page, null);
        // 加载用户角色信息
        PageResult pageResult = new PageResult(result);
        return pageResult;
    }

    @Override
    public int delete(List<SysDept> records) {
        for(SysDept record : records) {
            removeById(record.getId());
        }
        return 1;
    }

    @Override
    public List<SysDept> findTree(String name) {
        List<SysDept> sysDepts = new ArrayList<>();
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        boolean isSearch = false;
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(SysDept::getName, name);
            isSearch = true;
        }
        List<SysDept> depts = this.list(wrapper);
        if (isSearch) {
            // 子节点匹配但父节点没匹配 把父节点加入
            List<SysDept> needAddList = new ArrayList<>();
            for (SysDept dept : depts) {
                if (dept.getParentId() != null && dept.getParentId() != 0) {
                    SysDept parent = getById(dept.getParentId());
                    if (parent != null && !depts.contains(parent)) {
                        needAddList.add(parent);
                    }
                }
            }
            depts.addAll(needAddList);
        }
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                dept.setLevel(0);
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
        return sysDepts;
    }

    private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
        for (SysDept sysDept : sysDepts) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(dept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }
}
