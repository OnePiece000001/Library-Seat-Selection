package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.JobOption;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping ("/report")
@RestController
public class ReportController {
   @Autowired
   private ResultService resultService;
   //获取员工职位统计信息
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        JobOption jobOption = resultService.getEmpJobData();
        return Result.success(jobOption);
        
    }

    //获取员工性别信息
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        List<Map<String,Object>> empGenderData= resultService.getEmpGenderData();
        return Result.success(empGenderData);
    }

}
