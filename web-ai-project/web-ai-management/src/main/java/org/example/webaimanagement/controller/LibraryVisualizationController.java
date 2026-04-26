package org.example.webaimanagement.controller;

import org.example.webaimanagement.mapper.LibraryCollectMapper;
import org.example.webaimanagement.pojo.LibraryAllChart;
import org.example.webaimanagement.pojo.LibraryAllMap;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.LibraryCollectService;
import org.example.webaimanagement.service.LibraryVisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryVisualizationController {
    @Autowired
    LibraryVisualizationService libraryVisualizationService;
    //获得图表可视化数据
    @GetMapping("/getAllDataChart")
    public Result getCollect() {
        List<LibraryAllChart> datas= libraryVisualizationService.getAllDataChart();
        return !datas.isEmpty() ? Result.success(datas) : Result.error("数据获取是失败，请联系管理员！");
    }
    //获取可视化大屏数据
    @GetMapping("/getAllMap")
    public Result getCollect(String user_phone) {
        List<LibraryAllMap> datas= libraryVisualizationService.getAllMap();
        return !datas.isEmpty() ? Result.success(datas) : Result.error("数据获取失败，请联系管理员！");
    }
}
