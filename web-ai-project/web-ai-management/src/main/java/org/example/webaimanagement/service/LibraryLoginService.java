package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.LibraryUser;
import org.example.webaimanagement.pojo.User;

public interface LibraryLoginService {
    LibraryUser login(String userphone, String password);

    Integer updateuserinfo(LibraryUser libraryUser);
}
