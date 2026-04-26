package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.Result;

import org.example.webaimanagement.pojo.UserOrder;
import org.example.webaimanagement.pojo.UserOrderQuery;
import org.example.webaimanagement.service.StudentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StudentOrderController {
    @Autowired
    private StudentOrderService studentOrderService;
    @GetMapping("/queryUseApi")
    public Result queryUseApi(String account, String nowtime) {

        UserOrder userorder=studentOrderService.getusermsg(account,nowtime);
        return userorder != null ? Result.success(userorder) : Result.error("该用户没有此时间段的预约信息！");
    }
    @GetMapping("/insertUserChair")
    public Result insertUserChair(UserOrderQuery userOrderQuery) {
        Integer num = studentOrderService.insertUserChair(userOrderQuery);
        return num != 0 ? Result.success() : Result.error("预约信息存在问题！");
    }
    @GetMapping("/updateUser")
    public Result updateUser(UserOrderQuery userOrderQuery) {
        Integer num = studentOrderService.updateUser(userOrderQuery);
        return num != 0 ? Result.success() : Result.error("未查到需更新信息！");
    }
    @GetMapping("/deleteuserseat")
    public Result deleteuserseat(String account, String nowtime) {
        Integer num = studentOrderService.deleteuserseat(account,nowtime);
        return num != 0 ? Result.success() : Result.error("未查到需要删除内容！");
    }
    @GetMapping("/getsettingstu")
    public Result getsettingstu(String nowtime) {

        List<UserOrder> userorders=studentOrderService.getsettingstu(nowtime);
        return userorders != null ? Result.success(userorders) : Result.error("该用户没有此时间段的预约信息！");
    }
    @GetMapping("/deleteONEstudent")
    public Result deleteONEstudent(Integer studentId, String startTime) {
        Integer num = studentOrderService.deleteONEstudent(studentId,startTime);
        System.out.println(num);
        return num != 0 ? Result.success() : Result.error("未查到需要删除内容！");
    }
//    @DeleteMapping
//    //数组不能省略@RequestParam
//    public Result deleteMANYstudent(@RequestParam List<Integer> ids){
//        System.out.println(ids);.delete(ids);
//        return Result.success();
//
//    }
}
