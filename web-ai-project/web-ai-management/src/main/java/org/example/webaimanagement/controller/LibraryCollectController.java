package org.example.webaimanagement.controller;

import org.example.webaimanagement.mapper.LibraryCollectMapper;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBorrow;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.LibraryBorrowService;
import org.example.webaimanagement.service.LibraryCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryCollectController {
    @Autowired
    LibraryCollectService libraryCollectService;

    //获得指定学生的收藏信息
    @GetMapping("/getShouCangbook")
    public Result getCollect(String user_phone) {
        List<LibraryBook> collects= libraryCollectService.getCollectmsg(user_phone);
        return !collects.isEmpty() ? Result.success(collects) : Result.error("没有找到相关数据！");
    }
    //添加收藏信息
    @PostMapping("/addShouCangbook")
    public Result addCollectbook(String user_phone,String book_no) {
        Integer Collect= libraryCollectService.addCollectBook(user_phone,book_no);
        return Collect !=0 ? Result.success("添加成功！") : Result.error("没有找到相关数据！");
    }
        //删除收藏信息
        @Autowired
         LibraryCollectMapper libraryCollectMapper;
    @DeleteMapping("/deleteShouCangbook")
    public Result deleltecollect(String user_phone,String book_no) {
        Integer collect= libraryCollectMapper.deleteCollect_phone_book_no(user_phone,book_no);
        return collect !=0 ? Result.success("删除成功！") : Result.error("没有找到相关数据！");
    }



//    //获得所有学生的借阅信息
//    @GetMapping("/manageuserborrow")
//    public Result getAllborrow() {
//        List<LibraryBorrow> borrows = libraryBorrowservice.getAllBorrow1();
//        return !borrows.isEmpty() ? Result.success(borrows) : Result.error("没有找到相关数据！");
//    }
//    //修改借阅信息
//    @PostMapping("/updateuserborrow")
//    public Result updateborrow(LibraryBorrow libraryBorrow) {
//        Integer borrow= libraryBorrowservice.updateBorrow(libraryBorrow);
//        return borrow !=0 ? Result.success("更新成功！") : Result.error("没有找到相关数据！");
//    }



}
