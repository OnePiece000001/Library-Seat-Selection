package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ResultService {

    JobOption getEmpJobData();


    List<Map<String, Object>> getEmpGenderData();
}
