package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.Book;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBookPhotoParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface LibraryBookService {

    List<LibraryBook> getbook();

    List<LibraryBook> getbook(String userPhone);


    List<LibraryBook> getbook1();

    Integer updatebook(LibraryBookPhotoParam libraryBook);

    Integer deletebook(String book_id);

    Integer updateBookphoto(String url,String bookId);

    Integer addbook(LibraryBookPhotoParam libraryBook);

    LibraryBook searchBook(String book_name);

    List<LibraryBook> getAllbook();

    List<LibraryBook> gettopbooks();

    List<LibraryBook> getDefaultBooks();

    Integer collectbook(String bookNo);
}
