package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.LibraryBorrow;

import java.util.List;

public interface LibraryBorrowService {



    List<LibraryBorrow> getAllBorrow(String user_phone,String user_name);

    List<LibraryBorrow> getAllBorrow1();

    Integer updateBorrow(LibraryBorrow libraryBorrow);

    Integer deleteUserBorrow(LibraryBorrow libraryBorrow);

    Integer addBorrow(LibraryBorrow libraryBorrow);
}
