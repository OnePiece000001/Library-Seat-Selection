package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.webaimanagement.pojo.Book;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book")
    List<Book> getrecommendbook100();
    @Select("select * from book where bookid =#{bookid}")
    Book getBookInfoById(Integer booid);
}
