package org.example.webaimanagement.service;


import org.example.webaimanagement.pojo.UserOrder;
import org.example.webaimanagement.pojo.UserOrderQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentOrderService {
    UserOrder getusermsg(String account, String nowtime);

    Integer insertUserChair(UserOrderQuery userOrderQuery);

    Integer updateUser(UserOrderQuery userOrderQuery);

    Integer deleteuserseat(String account, String nowtime);

    List<UserOrder> getsettingstu(String nowtime);

    Integer deleteONEstudent(Integer studentId,String startTime);
}
