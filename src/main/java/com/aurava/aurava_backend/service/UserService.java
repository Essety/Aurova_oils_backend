package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.LoginRequest;
import com.aurava.aurava_backend.DTO.RegisterRequest;
import com.aurava.aurava_backend.entity.User;

public interface UserService {

    

    User register(RegisterRequest request);

    User login(LoginRequest request);

}