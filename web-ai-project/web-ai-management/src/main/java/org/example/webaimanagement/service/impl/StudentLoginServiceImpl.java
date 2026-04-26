package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.StudentLoginMapper;
import org.example.webaimanagement.pojo.User;
import org.example.webaimanagement.service.StudentLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudentLoginServiceImpl implements StudentLoginService {
    @Autowired
    StudentLoginMapper studentLoginMapper;
    @Override
    public User login(String account, String micode) {
        User user=studentLoginMapper.login(account,micode);
        return user;
    }

    @Override
    public Integer restpwd(String account, String newpassword) {
           return studentLoginMapper.restpwd(account,newpassword);
    }

    @Override
    public Integer zhuvejk(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return studentLoginMapper.zhucejk(user);
    }

    @Override
    public Integer selectphone(String account) {
        return studentLoginMapper.selectphone(account);
    }

    @Override
    public User getusermsg(String account) {
        return studentLoginMapper.getusermsg(account);
    }
}
