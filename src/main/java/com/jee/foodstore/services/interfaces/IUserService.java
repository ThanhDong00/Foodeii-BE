package com.jee.foodstore.services.interfaces;

import com.jee.foodstore.dtos.user.*;

import java.util.List;

public interface IUserService {
    UserDTO signUp(SignUpDTO signUpDTO);
    SignInResponseDTO signIn(SignInDTO signInDTO);
    UserDTO getProfile();
    UserDTO updateProfile(UserDTO userDTO);
    ChangePwdResponseDTO changePassword(ChangePwdRequestDTO changePwdRequestDTO);
    List<UserDTO> getAllUsers();
}
