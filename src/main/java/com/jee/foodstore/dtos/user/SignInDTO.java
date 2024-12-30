package com.jee.foodstore.dtos.user;

import lombok.Data;

@Data
public class SignInDTO {
    private String email;
    private String password;
}
