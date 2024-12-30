package com.jee.foodstore.dtos.user;

import lombok.Data;

@Data
public class ChangePwdResponseDTO {
    private String message;
    private UserDTO user;
}
