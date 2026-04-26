package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.webaimanagement.pojo.User;

@Mapper
public interface StudentLoginMapper {
    @Select("select * from library_dl where account=#{account} and micode =#{micode}")
    User login(String account, String micode);
    @Update("update library_dl set micode=#{newpassword} where account=#{account}")
    Integer restpwd(String account, String newpassword);
    @Insert("insert into library_dl(studentid,username,micode,account,ynadmin,create_time,update_time) values(#{studentId},#{username},#{micode},#{account},#{ynadmin},#{createTime},#{updateTime})")
    Integer zhucejk(User user);
    @Select("select * from library_dl where account =#{account}")
    Integer selectphone(String account);
    @Select("select * from library_dl where account =#{account}")
    User getusermsg(String account);
}
