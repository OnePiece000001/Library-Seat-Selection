package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.pojo.LibraryBooking;
import org.example.webaimanagement.pojo.ReserveTimeDTO;

import java.util.List;

public interface LibraryBookingService {



    List<LibraryBooking> getbooking(String userphone,String content);

    List<LibraryBooking> getAllBooking();

    Integer updatebooking(LibraryBooking libraryBooking);

    Integer deletebooking(String bookingID);

    Integer addbooking(LibraryBooking libraryBooking);

    List<ReserveTimeDTO> getMergedReserveTimes(String seat);
}
