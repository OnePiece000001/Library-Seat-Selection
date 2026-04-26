package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.webaimanagement.pojo.LibraryBook;

import java.util.List;

@Mapper
public interface LibraryCoVisualizationllectMapper {
    @Select("select book_id,book_no,book_name,book_author,book_photo,book_message,book_type from book_collect where user_phone=#{user_phone}")
    List<LibraryBook> getCollectbook_userPone(String userPhone);

}
