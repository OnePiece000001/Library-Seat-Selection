package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.webaimanagement.pojo.LoginQueryParam;
import org.example.webaimanagement.pojo.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> findAll(LoginQueryParam loginQueryParam);

    List<LoginQueryParam> findAllLogin(LoginQueryParam loginQueryParam);

    @Insert("insert into student_login(username,password) values " +
            "(#{username},#{password})")
    void insert(Student student);

    void deleteById(List<Integer> ids);
    @Insert("insert into student(username,floor,seat,start_Learning,end_Learning) values " +
            "(#{username},#{floor},#{seat},#{startLearning},#{endLearning})")
    void insertuser(Student student);
    //sql语句中还是用数据库中的字段名，在
    @Update("update student set floor=#{floor},seat=#{seat},start_Learning=#{startLearning},end_Learning=#{endLearning} where username=#{username}")
    void update(Student student);
}
