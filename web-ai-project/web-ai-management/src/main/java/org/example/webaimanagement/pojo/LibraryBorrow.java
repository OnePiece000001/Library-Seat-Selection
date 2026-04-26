package org.example.webaimanagement.pojo;

import lombok.Data;

@Data
public class LibraryBorrow {
  private String   book_no;
    private String  book_name ;
    private String  borrow_time ;
    private String   return_time;
    private String  return_status ;
    private String user_name;
    private String user_phone;
}
