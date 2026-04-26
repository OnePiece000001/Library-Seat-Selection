package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webaimanagement.pojo.Book;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBookPhotoParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Mapper
public interface LibraryBookMapper {
    @Select("select book_no,book_name,book_author,book_photo book_cover,book_message book_msg,book_num,book_type from book_info")
    List<LibraryBook> getbook();
    
    @Select("select book_no,book_name,book_author,book_photo book_cover,book_message book_msg,book_num,book_type from book_info")
    List<LibraryBook> getDefaultBooks();
    @Select("select book_id,book_name,book_author,book_photo,book_message,book_num from book_info")
    List<LibraryBook> getbook1();

    Integer updatebook(LibraryBookPhotoParam libraryBook);
    @Delete("delete from book_info where book_id=#{book_id}")
    Integer deleltebook(String book_id);
    @Update("update book_info set book_photo=#{book_photo}where book_id=#{book_id}")
    Integer updateBookphoto(String book_photo,String book_id);

    Integer addBook(LibraryBookPhotoParam libraryBook);
    @Select("select book_id,book_name,book_photo,book_author,book_message,book_num from book_info where book_name=#{bookName}")
    LibraryBook searchBook(String bookName);
    @Select("select book_id id,book_name,book_photo book_cover,book_author,book_type,book_no,book_message book_msg,book_num stock_num from book_info")
    List<LibraryBook> searchAllBook();
    @Select("select book_id id,book_name,book_photo book_cover,book_author,book_type,book_no,book_message book_msg,book_num stock_num from book_info limit 3;")
    List<LibraryBook> getTopBooks();

    // 图书库存 +1
    @Update("update book_info set book_num=book_num+1 where book_id = #{book_id}")
    void increaseBookNum(String book_id);
    @Insert("insert into collect_book_info( book_id,book_name,book_author,book_photo,book_message,book_type) values(#{book_id},#{book_name},#{book_author},#{book_photo},#{book_message},#{book_type})")
    Integer collectBook(LibraryBook libraryBook);
    //查询收藏信息
    @Select("select book_id,book_name,book_author,book_photo,book_message,book_type from book_info where book_no=#{book_no}")
    LibraryBook getcollectbook(String book_no);
    //插入收藏信息
    @Insert("INSERT INTO book_collect(book_id,book_no,book_name,book_author,book_photo,book_message,book_type,user_phone) values(#{book_id},#{book_id},#{book_name},#{book_author},#{book_photo},#{book_message},#{book_type},#{user_phone})")
    Integer insertcollectbook(LibraryBook libraryBook);
}
