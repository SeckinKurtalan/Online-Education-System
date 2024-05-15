package com.egeuniversity.onlineeducationsystem.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String photoLink;

}
