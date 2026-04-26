package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.DeptMapper;
import org.example.webaimanagement.pojo.Dept;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeptServiceimpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> findAll() {
        List<Dept> listDept=deptMapper.findAll();
        return listDept;

    }

    @Override
    public void delete(Integer id) {
        deptMapper.delete(id);
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDate.now());


        dept.setUpdateTime(LocalDate.now());
        deptMapper.insert(dept);

    }

    @Override
    public Dept getInfo(Integer id) {

        return  deptMapper.getByid(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDate.now());
        deptMapper.update(dept);
    }

}
