package org.example.webaimanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryBooking {
    private String user_name;
    private String user_phone;
    private String user_picture;
    private String bookingID;
    private String seat;
    private String floor;
    private String start_time;
    private String end_time;
    private LocalDateTime date;
    private String seat_status;

}
