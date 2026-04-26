//package org.example.webaimanagement.controller;
//
//import org.example.webaimanagement.pojo.*;
//import org.example.webaimanagement.service.EmpService;
//import org.example.webaimanagement.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//@RequestMapping("login")
//@RestController
//public class LoginController {
//    @Autowired
//    private StudentService studentService;
//    //插数据
//    @GetMapping
//    public Result getStudents(LoginQueryParam loginQueryParam) {
//        List<LoginQueryParam> listStudent = studentService.findAllLogin(loginQueryParam);
//        return Result.success(listStudent);
//    }
//    //验证有没有该用户
//    @PostMapping
//    public Result save(@RequestBody Student student) {
//
//        studentService.save(student);
//        return Result.success();
//    }
//}
