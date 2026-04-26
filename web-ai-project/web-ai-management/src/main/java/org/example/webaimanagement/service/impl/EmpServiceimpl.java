package org.example.webaimanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.webaimanagement.mapper.EmpExprMapper;
import org.example.webaimanagement.mapper.EmpMapper;
import org.example.webaimanagement.pojo.*;
import org.example.webaimanagement.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpServiceimpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //pom下载依赖使用
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        List<Emp> empList=empMapper.list(empQueryParam);
        //使用分页组件进行开发，不用对sql语句进行处理，通过组件控制查询的结果。这里是控制显示5条
        Page<Emp> p= (Page<Emp>)empList;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Autowired
    private EmpExprMapper empExprMapper;
    @Transactional
    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());

        empMapper.insert(emp);

        //这里指定了返回值类型，就相当于创建了EmpExpr对象，还是多个
        List<EmpExpr> empList=emp.getExprList();
        if(!CollectionUtils.isEmpty(empList)){
            empList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(empList);
        }

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        empMapper.deleteById(ids);
        empExprMapper.deleteByEmpId(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        Emp emp =empMapper.getById(id);

        return emp;
    }

    @Override
    public void update(Emp emp) {
        //员工基础数据修改
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //员工工作经历数据修改
        empExprMapper.deleteByEmpId(Arrays.asList(emp.getId()));
        List<EmpExpr> empList=emp.getExprList();
        if(!CollectionUtils.isEmpty(empList)){
            empList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(empList);

        }

    }

    @Override
    public LoginInfo login(Emp emp) {

       Emp e= empMapper.selectByUsernameAndPassword(emp);
        if(e != null){
            return new LoginInfo(e.getId(),e.getUsername(),e.getPassword(),"");
        }
        return null;
    }


}
