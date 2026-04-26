package org.example.webaimanagement.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class UserOrderQuery {
    private String  account;
    private String  nowfloor;
    private String  nowchairkey;
    @DateTimeFormat(pattern = "yyyy-M-d H:mm:ss")
    private LocalDateTime starttime;
    @DateTimeFormat(pattern = "yyyy-M-d H:mm:ss")
    private LocalDateTime endtime;
    @DateTimeFormat(pattern = "yyyy-M-d")
    private LocalDate nowtime;
    private Integer timenum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;       ;
}
