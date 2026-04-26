package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.User;

public interface StudentLoginService {
    User login(String account, String micode);

    Integer restpwd(String account, String newpassword);

    Integer zhuvejk(User user);

    Integer selectphone(String account);

    User getusermsg(String account);
}
