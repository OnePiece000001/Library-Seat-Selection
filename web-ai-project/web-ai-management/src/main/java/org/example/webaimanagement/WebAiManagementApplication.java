package org.example.webaimanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
// 补全@MapperScan的导入
import org.mybatis.spring.annotation.MapperScan;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
// 扫描mapper包下的所有MyBatis接口
@MapperScan("org.example.webaimanagement.mapper")
public class WebAiManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAiManagementApplication.class, args);
    }

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
}