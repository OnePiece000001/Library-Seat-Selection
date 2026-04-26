package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.webaimanagement.pojo.*;

import java.util.List;

@Mapper
public interface LibrarySeatDeskMapper{
    @Select("Select id,seat_no,seat_name,floor_no,seat_x,seat_y from seat_info")
    List<LibrarySeatDesk> getseat();
    @Select("Select id,desk_no,desk_name,floor_no,desk_x,desk_y,desk_type from desk_info")
    List<LibraryDeskSeat> getdesk();
    @Select("Select floor_no,floor_name from floor_info")
    List<LibraryFloor> getfloor();
    @Insert("insert into floor_info(floor_no,floor_name) values (#{floorNo},#{floorName})" )
    Integer addfloor(String floorName, String floorNo);

    Integer addDesk(List<LibraryDeskSeat> deskList);

    Integer addSeat(List<LibrarySeatDesk> seatList);
    @Delete("delete from seat_info where seat_name=#{seatName}")
    Integer deleteSeat(String seatName);

    @Delete("delete from floor_info where floor_no=#{floorNo} and floor_name=#{floorName}")
    Integer deleteFloor(String floorNo, String floorName);
    @Delete("delete from seat_info where floor_no=#{floorNo}")
    Integer deleteSeat_FloorNo(String floorNo);
    @Delete("delete from desk_info where floor_no=#{floorNo}")
    Integer deleteDesk_FloorNo(String floorNo);

    @Delete("delete from desk_info")
    void deleteAllDesk();
    @Delete("delete from seat_info")
    void deleteAllSeat();
    @Delete("delete from desk_info where desk_name=#{deskName}")
    Integer deleteDesk(String deskName);

    @Select("SELECT seat FROM booking_info WHERE NOW() BETWEEN start_time AND end_time")
    List<String> getOccupiedSeats();
}
