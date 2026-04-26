package org.example.webaimanagement.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LibraryBookPhotoParam {

    private String book_id;
    private String book_name;
    private String book_author;
    private MultipartFile book_photo;
    private String book_message;
    private String book_num;
    private String book_photo1;
    private String book_type;

}
