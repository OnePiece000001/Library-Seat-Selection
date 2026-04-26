package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.LibraryLoginMapper;
import org.example.webaimanagement.mapper.StudentLoginMapper;
import org.example.webaimanagement.pojo.LibraryUser;
import org.example.webaimanagement.pojo.User;
import org.example.webaimanagement.service.LibraryLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryLoginServiceImpl implements LibraryLoginService {
    @Autowired
    LibraryLoginMapper libraryLoginMapper;
    @Override
    public LibraryUser login(String userphone, String password) {
        LibraryUser libraryUser=libraryLoginMapper.login(userphone,password);
        return libraryUser;
    }

    @Override
    public Integer updateuserinfo(LibraryUser libraryUser) {
        return libraryLoginMapper.updateUserInfo(libraryUser);

    }

}
