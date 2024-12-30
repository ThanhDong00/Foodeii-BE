package com.jee.foodstore.dtos.user;

import lombok.Data;

@Data
public class SignUpDTO {
    private String email;
    private String name;
    private String password;
    private String role;
}
