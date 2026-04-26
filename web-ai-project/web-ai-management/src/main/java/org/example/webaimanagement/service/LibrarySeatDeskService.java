package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.LibraryDeskSeat;
import org.example.webaimanagement.pojo.LibraryDeskSeatList;
import org.example.webaimanagement.pojo.LibraryFloor;
import org.example.webaimanagement.pojo.LibrarySeatDesk;

import java.util.List;

public interface LibrarySeatDeskService {


    List<LibrarySeatDesk> getseat();

    List<LibraryDeskSeat> getdesk();

    List<LibraryFloor> getfloor();

    Integer addfloor(String floorName, String floorNo);

    Integer saveDeskSeat(LibraryDeskSeatList libraryDeskSeatList);

    Integer delelteSeat(String seatName);

    Integer delelteFloor(String floorNo, String floorName);

    Integer delelteDecsk(String deskName);

    List<String> getOccupieSeats();
}
