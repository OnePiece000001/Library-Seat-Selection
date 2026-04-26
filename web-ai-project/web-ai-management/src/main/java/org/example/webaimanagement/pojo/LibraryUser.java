package org.example.webaimanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryUser {
    private String user_name;
    private MultipartFile user_picture;
    private String user_type;
    private String password;
    private String user_newphone;
    private  String user_phone;
    private String user_picture1;
}
