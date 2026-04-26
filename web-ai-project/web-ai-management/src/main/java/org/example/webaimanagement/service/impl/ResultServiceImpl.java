package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.EmpMapper;
import org.example.webaimanagement.pojo.JobOption;
import org.example.webaimanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private EmpMapper empMapper;


    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list= empMapper.getEmpJobData();
     List<Object> jobList=list.stream().map(datamap -> datamap.get("pos") ).toList();
      List<Object> dataList=list.stream().map(datamap -> datamap.get("num") ).toList();
        return new  JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }
}
