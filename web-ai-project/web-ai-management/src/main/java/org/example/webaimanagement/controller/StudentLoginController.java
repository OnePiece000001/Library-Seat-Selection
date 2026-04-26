package org.example.webaimanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.pojo.User;
import org.example.webaimanagement.service.StudentLoginService;
import org.example.webaimanagement.service.StudentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@RestController
public class StudentLoginController {
    @Autowired
    StudentLoginService studentLoginService;
    @Autowired
    private StudentOrderService studentOrderService;

    @GetMapping("/login1")
    public Result login(String account, String micode) {

         User user = studentLoginService.login(account, micode);
        return user != null ? Result.success() : Result.error("亲，你输入的账号或密码有误！");
    }
    @GetMapping("/restpwd")
    public Result restpwd(String account, String newpassword) {

         Integer num=studentLoginService.restpwd(account, newpassword);
        return num != null ? Result.success() : Result.error("亲，密码已更新！");
    }
    @GetMapping("/zhucejk")
    public Result zhucejk(User user) {

        Integer num=studentLoginService.zhuvejk(user);
        return num != null ? Result.success() : Result.error("注册失败，请联系管理员！");
    }
    @GetMapping("/selectphone")
    public Result selectphone(String account) {

        Integer num=studentLoginService.selectphone(account);
        return num != null ? Result.success() : Result.error("该手机号未被注册！");
    }
    @GetMapping("/getusermsg")
    public Result getusermsg(String account) {

        User user=studentLoginService.getusermsg(account);
        return user != null ? Result.success(user) : Result.error("该手机未找到！");
    }

}
