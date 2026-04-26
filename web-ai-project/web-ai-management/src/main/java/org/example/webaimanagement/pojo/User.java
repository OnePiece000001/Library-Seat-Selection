package org.example.webaimanagement.pojo;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
@Data
public class User {
    private Integer id;
    private BigInteger studentId;
    private String  username;
    private String  micode;
    private String  account;
    private Boolean ynadmin;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
