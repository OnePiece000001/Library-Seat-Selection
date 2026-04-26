package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.*;
import org.example.webaimanagement.service.DeptService;
import org.example.webaimanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping
    public Result getStudents(LoginQueryParam loginQueryParam) {
        List<Student> listStudent = studentService.findAll(loginQueryParam);
        System.out.println(listStudent);
        return Result.success(listStudent);
    }
    @PostMapping
    public Result save(@RequestBody Student student) {
        studentService.saveuser(student);

       return Result.success();
    }

    /**
     * student删除
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        System.out.println(ids);
        studentService.delete(ids);
        return Result.success();

    }
    @PutMapping
    public Result update(@RequestBody Student student) {

        studentService.update(student);
        return Result.success();
    }

}
