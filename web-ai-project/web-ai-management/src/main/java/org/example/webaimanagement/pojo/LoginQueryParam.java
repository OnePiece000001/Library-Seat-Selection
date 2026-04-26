package org.example.webaimanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginQueryParam {
    private Boolean userStatus;
    private String  username;
    private String  password;
}
