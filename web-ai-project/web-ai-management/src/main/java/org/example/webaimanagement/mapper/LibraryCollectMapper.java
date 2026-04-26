package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBorrow;

import java.util.List;

@Mapper
public interface LibraryCollectMapper {
    @Select("select book_id,book_no,book_name,book_author,book_photo,book_message,book_type from book_collect where user_phone=#{user_phone}")
    List<LibraryBook> getCollectbook_userPone(String userPhone);
    @Delete("delete     from book_collect where user_phone=#{userPhone} and book_no=#{bookNo}")
    Integer deleteCollect_phone_book_no(String userPhone, String bookNo);

//
//    @Select("select book_no,book_name,borrow_time,return_time,return_status from book_borrow where user_phone=#{user_phone} and user_name=#{user_name}")
//    List<LibraryBorrow> getAllBorrow(String user_phone,String user_name);
//    @Select("select book_no,book_name,borrow_time,return_time,return_status,user_name,user_phone from book_borrow")
//    List<LibraryBorrow> getAllBorrow1();
//    @Update("update book_borrow set user_name=#{user_name},user_phone=#{user_phone},book_name=#{book_name},book_no=#{book_no},borrow_time=#{borrow_time},return_time=#{return_time},return_status=#{return_status} where book_no=#{book_no}")
//    Integer updateBorrow(LibraryBorrow libraryBorrow);
//    @Delete("delete from book_borrow where book_no=#{book_no} and user_phone=#{user_phone} and user_name=#{user_name} ")
//    Integer deleteUserBorrow(LibraryBorrow libraryBorrow);
//    @Insert("INSERT INTO book_borrow(user_name,user_phone,borrow_time,return_time,book_no,book_name) values(#{user_name},#{user_phone},#{borrow_time},#{return_time},#{book_no},#{book_name})")
//    Integer addBorrow(LibraryBorrow libraryBorrow);

}
