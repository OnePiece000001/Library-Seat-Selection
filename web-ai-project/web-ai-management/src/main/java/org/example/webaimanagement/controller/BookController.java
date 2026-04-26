package org.example.webaimanagement.controller;

import org.example.webaimanagement.pojo.Book;
import org.example.webaimanagement.pojo.Result;
import org.example.webaimanagement.service.BookService;
import org.example.webaimanagement.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @GetMapping("/getrecommendbook100")
    public Result getrecommendbook100() {

          List<Book> book=bookService.getrecommendbook100();
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }
    @GetMapping("/getBookInfoById")
    public Result getBookInfoById(Integer bookid) {

        Book book=bookService.getBookInfoById(bookid);
        return book != null ? Result.success(book) : Result.error("没有找到相关数据！");
    }
}

