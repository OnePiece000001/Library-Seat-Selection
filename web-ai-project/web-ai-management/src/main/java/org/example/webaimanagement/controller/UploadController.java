package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {
////        本地上传测试
//    @PostMapping("/upload")
//    public Result upload(String name, Integer age, MultipartFile file) throws IOException{
//        String originalFileName = file.getOriginalFilename();
//        String extName = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString() + extName;
//        file.transferTo(new File("D:/images/"+newFileName));
//        return Result.success();
//    }

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        String url= aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        System.out.println(url);
        return Result.success(url);}
}


