package org.example.webaimanagement.pojo;

import lombok.Data;

@Data
public class Book {
    private Integer id;
    private String bookname;   // 书名
    private String bookphoto;  // 书籍图片路径
    private Integer bookid;    // 书籍编号（INT类型）
    private String zuozhe;     // 作者
    private String shujiahao;  // 书架号
    private String jianjie;    // 书籍简介

}
