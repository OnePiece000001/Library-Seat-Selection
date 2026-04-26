package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webaimanagement.pojo.LibraryUser;
import org.example.webaimanagement.pojo.User;

@Mapper
public interface LibraryLoginMapper {
    @Select("select user_name,user_phone,user_picture,user_type from sys_user where user_phone=#{userphone} and password =#{password}")
    LibraryUser login(String userphone, String password);
    @Update("update sys_user set user_phone=#{user_newphone},user_name=#{user_name},user_picture=#{user_picture1},password=#{password} where user_phone=#{user_phone}")
    Integer updateUserInfo(LibraryUser libraryUser);
    @Insert("INSERT INTO sys_user(user_phone,user_name,password,user_picture) values (#{user_phone},#{user_name},#{password},#{user_picture1})")
    Integer register(LibraryUser libraryUser);
    @Results({
            // 字段名 -> 属性名 手动映射
            @Result(column = "user_name", property = "user_name"),
            @Result(column = "user_phone", property = "user_phone"),
            // 核心：数据库字段 user_picture → 实体属性 userPicture1
            @Result(column = "user_picture", property = "user_picture1"),
            @Result(column = "user_type", property = "user_type")
    })
    @Select("select user_name,user_phone,user_picture,user_type from sys_user where user_phone=#{user_phone}")
    LibraryUser getuserinfo(String user_phone);
}
