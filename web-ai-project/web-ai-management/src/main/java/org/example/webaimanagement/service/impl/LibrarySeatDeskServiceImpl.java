package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.LibraryBookMapper;
import org.example.webaimanagement.mapper.LibraryBookingMapper;
import org.example.webaimanagement.mapper.LibrarySeatDeskMapper;
import org.example.webaimanagement.pojo.*;
import org.example.webaimanagement.service.LibrarySeatDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibrarySeatDeskServiceImpl implements LibrarySeatDeskService {
     @Autowired
     private LibrarySeatDeskMapper librarySeatDeskMapper;


    @Override
    public List<LibrarySeatDesk> getseat() {
        return librarySeatDeskMapper.getseat();
    }

    @Override
    public List<LibraryDeskSeat> getdesk() {
        return librarySeatDeskMapper.getdesk();
    }

    @Override
    public List<LibraryFloor> getfloor() {
        return librarySeatDeskMapper.getfloor();
    }

    @Override
    public Integer addfloor(String floorName, String floorNo) {
        return librarySeatDeskMapper.addfloor(floorName,floorNo);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveDeskSeat(LibraryDeskSeatList libraryDeskSeatList){
        List<LibrarySeatDesk> seatList=libraryDeskSeatList.getSeatList();
        List<LibraryDeskSeat> deskList=libraryDeskSeatList.getDeskList();
         librarySeatDeskMapper.deleteAllDesk();
        Integer a= librarySeatDeskMapper.addDesk(deskList);
        librarySeatDeskMapper.deleteAllSeat();
        Integer b=librarySeatDeskMapper.addSeat(seatList);

        return a+b !=0 ? 1 : 0;

    }
     @Autowired
     LibraryBookingMapper libraryBookingMapper;
    @Override
    public Integer delelteSeat(String seatName) {
        libraryBookingMapper.deleteBooking_seatName(seatName);
        return librarySeatDeskMapper.deleteSeat(seatName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer delelteFloor(String floorNo,String floorName) {
      Integer a= librarySeatDeskMapper.deleteFloor(floorNo,floorName);
      Integer b= librarySeatDeskMapper.deleteSeat_FloorNo(floorNo);
      Integer c= librarySeatDeskMapper.deleteDesk_FloorNo(floorNo);
      Integer d=libraryBookingMapper.deleteBooking_floor(floorNo);
      return a+b+c+d !=0 ? a+b+c :  0 ;
    }

    @Override
    public Integer delelteDecsk(String deskName) {
        return librarySeatDeskMapper.deleteDesk(deskName);
    }

    @Override
    public List<String> getOccupieSeats() {
        return librarySeatDeskMapper.getOccupiedSeats();
    }


}
