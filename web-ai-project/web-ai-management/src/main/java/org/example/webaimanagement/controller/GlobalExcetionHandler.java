package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class  GlobalExcetionHandler {
    @ExceptionHandler
    public Result exception(Exception e) {
        System.out.println("错误为："+e.getMessage());
        return Result.error("出错了，亲，请联系管理员！");
    }
    @ExceptionHandler
    public Result handleDuplicateKayExcetion(DuplicateKeyException e) {
        String msg = e.getMessage();
        int i = msg.indexOf("Duplicate entry");
        String errMsg = msg.substring(i);
        String[] errList = errMsg.split(" ");
        String errCode = errList[2]+"已存在";

        return Result.error(errCode);
    }

}
