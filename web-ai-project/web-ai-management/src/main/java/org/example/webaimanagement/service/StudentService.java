package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.*;

import java.util.List;

public interface StudentService {
    List<Student> findAll(LoginQueryParam loginQueryParam);

    void save(Student student);
    void saveuser(Student student);
    void delete(List<Integer> ids);


    List<LoginQueryParam> findAllLogin(LoginQueryParam loginQueryParam);

    void update(Student student);
}
