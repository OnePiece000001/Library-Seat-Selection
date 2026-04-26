package org.example.webaimanagement.controller;

import org.example.webaimanagement.mapper.LibraryBookingMapper;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.ReserveTimeDTO;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.LibraryBookService;
import org.example.webaimanagement.service.LibraryBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryBookingController {
    @Autowired
    LibraryBookingService libraryBookingService;
    //获取时间段，用于判断已存在选座时间
    @GetMapping("/getbookingtime")
    public Result getbookingtime(String seat) {
        List<ReserveTimeDTO>data= libraryBookingService.getMergedReserveTimes(seat);
        return data != null ? Result.success(data) : Result.error("没有找到相关数据！");
    }

    //根据手机号查找相应学生的预约信息
    @PostMapping("/bookingmessage")
    public Result getbooking(String userphone,String historyYOrN) {

        List<LibraryBooking> book = libraryBookingService.getbooking(userphone,historyYOrN);
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }

    //获得所有学生的预约信息
    @PostMapping("/bookingmanage")
    public Result getAllbooking() {
        List<LibraryBooking> bookings = libraryBookingService.getAllBooking();
        return bookings != null ? Result.success(bookings) : Result.error("没有找到相关数据！");
    }

    //修改学生的预约信息
    @PostMapping("/updatebooking")
    public Result updatebooking(LibraryBooking libraryBooking) {
        Integer booking = libraryBookingService.updatebooking(libraryBooking);
        return booking != null ? Result.success(booking) : Result.error("没有找到相关数据！");
    }


    //插入预约信息
    @PostMapping("/addbooking")
    public Result addbooking(LibraryBooking libraryBooking) {
        Integer addBooking = libraryBookingService.addbooking(libraryBooking);
        return addBooking != 0 ? Result.success("预约成功！") : Result.error("没有找到相关数据！");
    }
    //删除指定预约信息并插入历史表
    @DeleteMapping("/deletebooking")
    public Result deletebooking(String bookingID) {
        Integer deleteBooking = libraryBookingService.deletebooking(bookingID);
        return deleteBooking != null ? Result.success(deleteBooking) : Result.error("没有找到相关数据！");
    }
   //实现签到更改为已完成
    @Autowired
    LibraryBookingMapper libraryBookingMapper;
    @PostMapping("/qianDao")
    public Result updatebooking(String seat_name,String user_phone) {
        Integer booking = libraryBookingMapper.updateBooking_seatName_userPhone(seat_name,user_phone);
        return booking != 0 ? Result.success("签到成功！") : Result.error("没有找到相关数据！");
    }



}
