package com.jee.foodstore.dtos.user;

import lombok.Data;

@Data
public class SignInResponseDTO {
    private String token;
    private UserDTO user;
}
