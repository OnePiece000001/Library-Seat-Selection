package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.LibraryBookMapper;
import org.example.webaimanagement.mapper.LibraryBookingMapper;
import org.example.webaimanagement.mapper.LibraryBorrowMapper;
import org.example.webaimanagement.pojo.LibraryBookPhotoParam;
import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.LibraryBorrow;
import org.example.webaimanagement.service.LibraryBookService;
import org.example.webaimanagement.service.LibraryBookingService;
import org.example.webaimanagement.service.LibraryBorrowService;
import org.example.webaimanagement.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryBorrowServiceImpl implements LibraryBorrowService{
   @Autowired
    private LibraryBorrowMapper libraryBorrowMapper;

    @Override
    public List<LibraryBorrow> getAllBorrow(String user_phone,String user_name) {
        return libraryBorrowMapper.getAllBorrow(user_phone,user_name);
    }

    @Override
    public List<LibraryBorrow> getAllBorrow1() {
        return libraryBorrowMapper.getAllBorrow1();
    }
    @Autowired
    private LibraryBookMapper libraryBookMapper;
    @Override
    public Integer updateBorrow(LibraryBorrow libraryBorrow) {
        // 正确写法：常量在前，用equals()比较内容
        if ("已归还".equals(libraryBorrow.getReturn_status())) {
            libraryBookMapper.increaseBookNum(libraryBorrow.getBook_no());
        }
        return libraryBorrowMapper.updateBorrow(libraryBorrow);
    }

    @Override
    public Integer deleteUserBorrow(LibraryBorrow libraryBorrow) {
        Integer a = libraryBorrowMapper.deleteUserBorrow(libraryBorrow);
        return a;
    }

    @Override
    public Integer addBorrow(LibraryBorrow libraryBorrow) {
        String borrow = DateUtil.convertJsDate(libraryBorrow.getBorrow_time());
        libraryBorrow.setBorrow_time(borrow);
        String returntime = DateUtil.convertJsDate(libraryBorrow.getReturn_time());
        libraryBorrow.setReturn_time(returntime);
        Integer a = libraryBorrowMapper.addBorrow(libraryBorrow);
        return a;
    }


}
