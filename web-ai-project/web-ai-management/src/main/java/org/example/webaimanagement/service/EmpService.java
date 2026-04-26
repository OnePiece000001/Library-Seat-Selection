package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.Emp;
import org.example.webaimanagement.pojo.EmpQueryParam;
import org.example.webaimanagement.pojo.LoginInfo;
import org.example.webaimanagement.pojo.PageResult;

import java.util.List;

public interface EmpService {
//    PageResult<Emp> page(Integer page, Integer pageSize);

    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);
//    PageResult page(Integer page, Integer pageSize);
}
