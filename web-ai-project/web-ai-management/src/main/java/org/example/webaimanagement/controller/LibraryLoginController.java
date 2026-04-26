package org.example.webaimanagement.controller;

import org.example.webaimanagement.mapper.LibraryLoginMapper;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryUser;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.pojo.User;
import org.example.webaimanagement.service.LibraryLoginService;
import org.example.webaimanagement.service.StudentLoginService;
import org.example.webaimanagement.service.StudentOrderService;
import org.example.webaimanagement.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class LibraryLoginController {
    @Autowired
    LibraryLoginService libraryLoginService;

    //登录
    @PostMapping("/login")
    public Result login(String userphone, String password) {


        LibraryUser libraryUser = libraryLoginService.login(userphone, password);
        return libraryUser != null ? Result.success(libraryUser) : Result.error("亲，你输入的账号或密码有误！");
    }

    //注册
    @Autowired
    private LibraryLoginMapper libraryLoginMapper;

    @PostMapping("/register")
    public Result register(LibraryUser libraryUser) throws Exception {
        MultipartFile userPhoto = libraryUser.getUser_picture();
        String url = null;

        // ========== 核心修复：判断文件是否为空 ==========
        if (userPhoto != null && !userPhoto.isEmpty()) {
            // 有图片才上传
            url = aliyunOSSOperator.upload(userPhoto.getBytes(), userPhoto.getOriginalFilename());
            libraryUser.setUser_picture1(url);
        } else {
            // 没图片 → 设为null 或 空字符串
            libraryUser.setUser_picture1(null);
        }

        Integer number = libraryLoginMapper.register(libraryUser);
        return number != 0 ? Result.success("亲，注册已完成！") : Result.error("亲，你输入的账号或密码有误！");
    }

    //修改个人信息
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/updateuserinfo")
    public Result updateuserinfo(LibraryUser libraryUser) throws Exception {
        MultipartFile userPhoto = libraryUser.getUser_picture();
        String url = null;

        // ========== 核心修复：判断文件是否为空 ==========
        if (userPhoto != null && !userPhoto.isEmpty()) {
            // 有图片才上传
            url = aliyunOSSOperator.upload(userPhoto.getBytes(), userPhoto.getOriginalFilename());
            libraryUser.setUser_picture1(url);
        } else {
            // 没图片 → 设为null 或 空字符串
            libraryUser.setUser_picture1(null);
        }

        Integer number = libraryLoginService.updateuserinfo(libraryUser);

        return number != 0 ? Result.success("亲，修改成功！") : Result.error("亲，修改失败，请联系管理员！");
    }

    @GetMapping("/getuserinfo")
    public Result gettopbook(String user_phone) {
        LibraryUser userInfo = libraryLoginMapper.getuserinfo(user_phone);
        return userInfo != null ? Result.success(userInfo) : Result.error("没有找到相关数据！");


    }
}
