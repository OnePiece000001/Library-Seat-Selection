package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.Book;

import java.util.List;

public interface BookService {

    List<Book> getrecommendbook100();

    Book getBookInfoById(Integer bookid);
}
