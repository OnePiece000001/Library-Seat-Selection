package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.LibraryBookMapper;

import org.example.webaimanagement.mapper.LibraryCollectMapper;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBorrow;
import org.example.webaimanagement.service.LibraryCollectService;
import org.example.webaimanagement.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryCollectServiceImpl implements LibraryCollectService {
   @Autowired
    private LibraryCollectMapper libraryCollectMapper;

//    @Override
//    public List<LibraryBorrow> getAllBorrow(String user_phone,String user_name) {
//        return libraryBorrowMapper.getAllBorrow(user_phone,user_name);
//    }
//
//    @Override
//    public List<LibraryBorrow> getAllBorrow1() {
//        return libraryBorrowMapper.getAllBorrow1();
//    }
//    @Autowired
//    private LibraryBookMapper libraryBookMapper;
//    @Override
//    public Integer updateBorrow(LibraryBorrow libraryBorrow) {
//        // 正确写法：常量在前，用equals()比较内容
//        if ("已归还".equals(libraryBorrow.getReturn_status())) {
//            libraryBookMapper.increaseBookNum(libraryBorrow.getBook_no());
//        }
//        return libraryBorrowMapper.updateBorrow(libraryBorrow);
//    }
//
//    @Override
//    public Integer deleteUserBorrow(LibraryBorrow libraryBorrow) {
//        Integer a = libraryBorrowMapper.deleteUserBorrow(libraryBorrow);
//        return a;
//    }
//
//    @Override
//    public Integer addBorrow(LibraryBorrow libraryBorrow) {
//        String borrow = DateUtil.convertJsDate(libraryBorrow.getBorrow_time());
//        libraryBorrow.setBorrow_time(borrow);
//        String returntime = DateUtil.convertJsDate(libraryBorrow.getReturn_time());
//        libraryBorrow.setReturn_time(returntime);
//        Integer a = libraryBorrowMapper.addBorrow(libraryBorrow);
//        return a;
//    }
    @Autowired
    LibraryBookMapper libraryBookMapper;

    @Override
    public List<LibraryBook> getCollectmsg(String userPhone) {
        return libraryCollectMapper.getCollectbook_userPone(userPhone);
    }

    @Override
    public Integer addCollectBook(String userPhone, String bookNo) {
        LibraryBook libraryBook=libraryBookMapper.getcollectbook(bookNo);
        libraryBook.setUser_phone(userPhone);
        Integer number=libraryBookMapper.insertcollectbook(libraryBook);
        return number;
    }


}
