package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.BookMapper;
import org.example.webaimanagement.pojo.Book;
import org.example.webaimanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
     @Autowired
     private BookMapper bookMapper;
    @Override
    public List<Book> getrecommendbook100() {
        return bookMapper.getrecommendbook100();
    }

    @Override
    public Book getBookInfoById(Integer bookid) {
        return bookMapper.getBookInfoById(bookid);
    }
}
