package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.ReserveTimeDTO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LibraryBookingMapper {

    @Select("select bookingID,seat,floor,start_time,end_time,date,seat_status from booking_info where user_phone=#{userphone}")
    List<LibraryBooking> getbooking(String userphone);
    @Select("select bookingID,seat,floor,start_time,end_time,date,seat_status from history_booking_info where user_phone=#{userphone}")
    List<LibraryBooking> getHistorybooking(String userphone);

    @Select("select user_name,b.user_phone,user_picture,bookingID,seat,floor,start_time,end_time,date from booking_info b,sys_user s where b.user_phone=s.user_phone ")
    List<LibraryBooking> getAllBooking();
    @Update("update booking_info set seat=#{seat},floor=#{floor},start_time=#{start_time},end_time=#{end_time} where bookingID=#{bookingID}")
    Integer updateBooking(LibraryBooking libraryBooking);

    @Insert("INSERT INTO booking_info(user_phone,seat,floor,start_time,end_time,date) " +
            "VALUES(#{user_phone}, #{seat}, #{floor}, #{start_time}, #{end_time}, #{date})")
    Integer addBooking(LibraryBooking libraryBooking);
    @Delete("delete from booking_info where seat=#{seatName}")
    Integer deleteBooking_seatName(String seatName);

    @Select("SELECT start_time , end_time " +
            "FROM booking_info " +  // 改成你真实表名
            "WHERE seat = #{seat} " +
            "  AND end_time >= #{nowTime} " +
            "ORDER BY start_time ASC")
    List<ReserveTimeDTO> getBookingTime(String seat, LocalDateTime nowTime);
    @Delete("delete from booking_info where floor=#{floor}")
    Integer deleteBooking_floor(String floor);

    @Select("select bookingID,user_phone,seat,floor,start_time,end_time,date,seat_status from booking_info where bookingID=#{bookingId}")
    LibraryBooking getbooking_bookingid(String bookingId);
    @Insert("INSERT INTO history_booking_info(bookingID,user_phone,seat,floor,start_time,end_time,date,seat_status) VALUES (#{bookingID}, #{user_phone}, #{seat},#{floor},#{start_time},#{end_time},#{date},'已取消')")
    void insertHistoryBooking(LibraryBooking libraryBooking);
    @Update("UPDATE booking_info " +
            "SET seat_status = '已签到' " +
            "WHERE bookingID = (" +
            "    SELECT bookingID FROM (" +
            "        SELECT bookingID FROM booking_info " +
            "        WHERE user_phone = #{user_phone} AND seat = #{seat_name} AND seat_status != '已签到' " +
            "        ORDER BY start_time ASC " +
            "        LIMIT 1" +
            "    ) AS temp" +
            ")")
    Integer updateBooking_seatName_userPhone(String seat_name,String user_phone);
    //删除预约
    @Delete("delete from booking_info where bookingID=#{bookingID}")
    Integer deleteBooking(String bookingID);
    //超时
    /**
     * 查询【超时10分钟未签到】的预约
     * 条件：未签到、开始时间 <= 当前时间-10分钟
     * 适配你start_time为String的情况，用STR_TO_DATE函数转换
     */
    @Select("SELECT bookingID FROM booking_info " +
            "WHERE seat_status = '未签到' " +
            "AND STR_TO_DATE(start_time, '%Y-%m-%d %H:%i') <= DATE_SUB(NOW(), INTERVAL 10 MINUTE)")
    List<String> getTimeoutUncheckBookings();
    //到预约结束时间的
    /**
     * 查询【已到结束时间】的预约
     * 条件：结束时间 <= 当前时间
     * 适配你end_time为String的情况，用STR_TO_DATE函数转换
     */
    @Select("SELECT bookingID FROM booking_info " +
            "WHERE STR_TO_DATE(end_time, '%Y-%m-%d %H:%i') <= NOW() and seat_status = '已签到' ")
    List<String> getEndTimeBookings();
    /**
     * 插入历史表（定时任务用，支持自定义状态：已取消/已结束）
     */
    @Insert("INSERT INTO history_booking_info(bookingID,user_phone,seat,floor,start_time,end_time,date,seat_status) " +
            "VALUES (#{bookingID},#{user_phone},#{seat},#{floor},#{start_time},#{end_time},#{date},#{seat_status})")
    void insertHistoryBookingWithStatus(LibraryBooking libraryBooking);
}

