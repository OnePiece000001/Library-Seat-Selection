package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.webaimanagement.pojo.Emp;
import org.example.webaimanagement.pojo.EmpQueryParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    public List<Emp> list(EmpQueryParam empQueryParam);



    //获取插入数据的生成的自增主键的值，并赋值给id属性
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username,name,gender,phone,job,salary,image,dept_id,entry_date,create_time,update_time) values " +
            "(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{deptId},#{entryDate},#{createTime},#{updateTime})")
    void insert(Emp emp);

    void deleteById(List<Integer> ids);

    Emp getById(Integer id);

    void updateById(Emp emp);


    List<Map<String,Object>> getEmpJobData();

    List<Map<String, Object>> countEmpGenderData();



    @Select("select id,username,name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
