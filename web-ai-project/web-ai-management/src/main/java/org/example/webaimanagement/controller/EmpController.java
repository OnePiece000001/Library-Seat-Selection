package org.example.webaimanagement.controller;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.example.webaimanagement.pojo.Emp;
import org.example.webaimanagement.pojo.EmpQueryParam;
import org.example.webaimanagement.pojo.PageResult;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.DeptService;
import org.example.webaimanagement.service.EmpService;
import org.example.webaimanagement.service.impl.EmpServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;
    @Autowired
    private EmpServiceimpl empServiceimpl;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
         PageResult<Emp> pageResult= empService.page(empQueryParam);
          return  Result.success(pageResult);
    }

    @PostMapping
    //请求参数json格式，需要添加@RequestBody
    public Result save(@RequestBody  Emp emp) {
        System.out.println(emp);
        empService.save(emp);
        return Result.success();
    }



    /**
     * 员工删除
     */
    @DeleteMapping
    //数组不能省略@RequestParam
    public Result delete(@RequestParam List<Integer> ids){
        System.out.println(ids);
        empService.delete(ids);
        return Result.success();

    }

    /**
     * 编辑数据回显
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        System.out.println(id);
        Emp emp =empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 编辑修改数据
     */
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        System.out.println(emp);
        empService.update(emp);
        return Result.success();
    }
}
