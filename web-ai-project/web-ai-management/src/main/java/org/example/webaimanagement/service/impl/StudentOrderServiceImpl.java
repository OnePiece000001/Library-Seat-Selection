package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.StudentOrderMapper;
import org.example.webaimanagement.pojo.UserOrder;
import org.example.webaimanagement.pojo.UserOrderQuery;
import org.example.webaimanagement.service.StudentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentOrderServiceImpl implements StudentOrderService {
    @Autowired
    private StudentOrderMapper studentOrderMapper;
    @Override
    public UserOrder getusermsg(String account, String nowtime) {
        return studentOrderMapper.getusermsg(account,nowtime);
    }

    @Override
    public Integer insertUserChair(UserOrderQuery userOrderQuery) {

        Duration duration = Duration.between(userOrderQuery.getStarttime(), userOrderQuery.getEndtime());
        userOrderQuery.setTimenum((int) duration.toMinutes());
        userOrderQuery.setCreateTime(LocalDateTime.now());
        userOrderQuery.setUpdateTime(LocalDateTime.now());
        userOrderQuery.setNowtime(userOrderQuery.getStarttime().toLocalDate());
        return studentOrderMapper.insertUserChair(userOrderQuery);
    }

    @Override
    public Integer updateUser(UserOrderQuery userOrderQuery) {
        Duration duration = Duration.between(userOrderQuery.getStarttime(), userOrderQuery.getEndtime());
        userOrderQuery.setTimenum((int) duration.toMinutes());
        userOrderQuery.setUpdateTime(LocalDateTime.now());
        userOrderQuery.setNowtime(userOrderQuery.getStarttime().toLocalDate());
        return studentOrderMapper.updateUser(userOrderQuery);
    }

    @Override
    public Integer deleteuserseat(String account, String nowtime) {
        return studentOrderMapper.deleteuserseat(account,nowtime);
    }

    @Override
    public List<UserOrder> getsettingstu(String nowtime) {
        return studentOrderMapper.getsettingstu(nowtime);
    }

    @Override
    public Integer deleteONEstudent(Integer studentId,String startTime) {
        return studentOrderMapper.deleteONEstudent(studentId,startTime);
    }
}
