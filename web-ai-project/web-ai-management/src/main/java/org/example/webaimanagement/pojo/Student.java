package org.example.webaimanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Student {
    private Integer id; //ID,主键
    private String username; //用户名
    private String password; //密码
    private String image; //头像
    private String floor; //姓名
    private String seat; //手机号
    private Integer todayLearning; //今日学习时长
    private Integer cumulativeLearning; //积累学习时长
    private LocalDateTime startLearning; //入座时间
    private LocalDateTime endLearning; //离座时间
    private Boolean userStatus;
}
