package org.example.webaimanagement.pojo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserOrder {
    private Integer id;
    private String  account;
    private LocalDateTime startLearning;
    private LocalDateTime endLearning;
    private String  floor;
    private String  seat;
    private Integer timeLearning;
    private LocalDate dateToday;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
