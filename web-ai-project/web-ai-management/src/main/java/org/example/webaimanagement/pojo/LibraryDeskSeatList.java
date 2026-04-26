package org.example.webaimanagement.pojo;

import lombok.Data;

import java.util.List;

@Data
public class LibraryDeskSeatList {
   private List<LibraryDeskSeat> deskList;
   private List<LibrarySeatDesk> seatList;
}
