package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webaimanagement.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Select("select * from dept order by update_time desc")
    List<Dept> findAll();
    @Delete("delete from dept where id = #{id}")
    void delete(Integer id);
    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);
    @Select("select * from dept where id = #{id}")
    Dept getByid(Integer id);
    //sql语句中还是用数据库中的字段名，在
    @Update("update dept set name=#{name},update_Time=#{updateTime} where id=#{id}")
    void update(Dept dept);
}
