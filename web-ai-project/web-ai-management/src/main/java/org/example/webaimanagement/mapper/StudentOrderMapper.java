package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;

import org.example.webaimanagement.pojo.UserOrder;
import org.example.webaimanagement.pojo.UserOrderQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StudentOrderMapper {
    @Select("select * from library_yy where account=#{account} and #{nowtime} between start_learning and end_learning ")
    UserOrder getusermsg(String account, String nowtime);
    @Insert("insert into library_yy(account,start_learning,end_learning,floor,seat,time_learning,date_today,create_time,update_time) values(#{account},#{starttime},#{endtime},#{nowfloor},#{nowchairkey},#{timenum},#{nowtime},#{createTime},#{updateTime})")
    Integer insertUserChair(UserOrderQuery userOrderQuery);
    @Update("update library_yy set floor=#{nowfloor},seat=#{nowchairkey},start_learning=#{starttime},end_learning=#{endtime},time_learning=#{timenum},date_today=#{nowtime},update_time=#{updateTime}  where account=#{account}")
    Integer updateUser(UserOrderQuery userOrderQuery);
    @Delete("delete from library_yy where account=#{account} and #{nowtime} between start_learning and end_learning")
    Integer deleteuserseat(String account, String nowtime);
    @Select("select * from library_yy where  #{nowtime} between start_learning and end_learning ")
    List<UserOrder> getsettingstu(String nowtime);
    @Delete("""
    DELETE FROM library_yy 
    WHERE studentid =#{studentId}  
    AND start_learning =  #{startTime}
""")
    Integer deleteONEstudent(Integer studentId, String startTime);

}
