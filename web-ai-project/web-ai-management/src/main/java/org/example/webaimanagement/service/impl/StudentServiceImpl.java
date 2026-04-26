package org.example.webaimanagement.service.impl;


import org.example.webaimanagement.mapper.StudentMapper;
import org.example.webaimanagement.pojo.Emp;
import org.example.webaimanagement.pojo.LoginInfo;
import org.example.webaimanagement.pojo.LoginQueryParam;
import org.example.webaimanagement.pojo.Student;
import org.example.webaimanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentMapper studentMapper;



    @Override
    public List<Student> findAll(LoginQueryParam loginQueryParam) {
        List<Student> listStudent=studentMapper.findAll(loginQueryParam);
        return listStudent;
    }

    @Override
    public void save(Student student) {
        studentMapper.insert(student);
    }


    @Override
    public void saveuser(Student student) {
        studentMapper.insertuser(student);
    }

    @Override
    public void delete(List<Integer> ids) {
        studentMapper.deleteById(ids);
    }

    @Override
    public List<LoginQueryParam> findAllLogin(LoginQueryParam loginQueryParam) {
        List<LoginQueryParam> listStudent=studentMapper.findAllLogin(loginQueryParam);
        return listStudent;
    }

    @Override
    public void update(Student student) {
        studentMapper.update(student);
    }


}
