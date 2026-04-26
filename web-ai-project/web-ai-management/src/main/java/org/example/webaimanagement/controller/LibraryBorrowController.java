package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.LibraryBorrow;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.LibraryBookingService;
import org.example.webaimanagement.service.LibraryBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryBorrowController {
    @Autowired
    LibraryBorrowService libraryBorrowservice;

    //获得指定学生的借阅信息
    @GetMapping("/getuserborrowmsg")
    public Result getborrow(String user_phone,String user_name) {
        List<LibraryBorrow> borrows = libraryBorrowservice.getAllBorrow(user_phone,user_name);
        return !borrows.isEmpty() ? Result.success(borrows) : Result.error("没有找到相关数据！");
    }
    //获得所有学生的借阅信息
    @GetMapping("/manageuserborrow")
    public Result getAllborrow() {
        List<LibraryBorrow> borrows = libraryBorrowservice.getAllBorrow1();
        return !borrows.isEmpty() ? Result.success(borrows) : Result.error("没有找到相关数据！");
    }
    //修改借阅信息
    @PostMapping("/updateuserborrow")
    public Result updateborrow(LibraryBorrow libraryBorrow) {
        Integer borrow= libraryBorrowservice.updateBorrow(libraryBorrow);
        return borrow !=0 ? Result.success("更新成功！") : Result.error("没有找到相关数据！");
    }
    //删除借阅信息
    @DeleteMapping("/deleteuserborrow")
    public Result delelteborrow(LibraryBorrow libraryBorrow) {
        Integer borrow= libraryBorrowservice.deleteUserBorrow(libraryBorrow);
        return borrow !=0 ? Result.success("删除成功！") : Result.error("没有找到相关数据！");
    }
    //添加借阅信息
    @PostMapping("/borrowbook")
    public Result addborrow(LibraryBorrow libraryBorrow) {
        Integer borrow= libraryBorrowservice.addBorrow(libraryBorrow);
        return borrow !=0 ? Result.success("删除成功！") : Result.error("没有找到相关数据！");
    }

}
