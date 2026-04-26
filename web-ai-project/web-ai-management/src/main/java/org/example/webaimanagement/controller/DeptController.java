package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.Dept;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    //    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping
    public Result getDepts() {
        List<Dept> listDept = deptService.findAll();
        return Result.success(listDept);
    }

    @DeleteMapping

    //方式三：将请求参数与形参参数名称一致，就能将参数值直接赋值到形参上，可省略@RequestParam，下面是方式二，方式一不推荐
    public Result delete(@RequestParam("id") Integer id) {
        System.out.println("你将要删除的部门号为：" + id);
        deptService.delete(id);
        return Result.success();
    }

    //这是通过post请求，痛过携带json参数，将参数通过@RequestBody直接赋值給形参，一般形参为对象类型
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        System.out.println("你将要添加的部门信息为" + dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    //{id}这种的叫路径参数，路径参数如果和方法形参一样，可省略@PathVariable("id")的（"id"）
    public Result get(@PathVariable Integer id) {
        System.out.println("该路径参数的值为" + id);
        Dept dept=deptService.getInfo(id);
        System.out.println(dept);

        return Result.success(dept);
    }
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        System.out.println("修改部门"+dept);
        deptService.update(dept);
        return Result.success();
    }
}