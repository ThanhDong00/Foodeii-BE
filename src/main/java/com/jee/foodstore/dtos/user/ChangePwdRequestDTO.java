package com.jee.foodstore.dtos.user;

import lombok.Data;

@Data
public class ChangePwdRequestDTO {
    private String oldPassword;
    private String newPassword;
}
