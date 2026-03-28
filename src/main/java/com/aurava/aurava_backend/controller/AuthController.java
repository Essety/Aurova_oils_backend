package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.DTO.*;
import com.aurava.aurava_backend.config.JwtUtil;
import com.aurava.aurava_backend.entity.User;
import com.aurava.aurava_backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
public RegisterResponse register(@Valid @RequestBody RegisterRequest request){

    User user = userService.register(request);

    return new RegisterResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getMobile(),
            user.getRole()
    );
}

    @PostMapping("/login")
public LoginResponse login(@Valid @RequestBody LoginRequest request){

    User user = userService.login(request);

    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

    return new LoginResponse(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getMobile(),
            user.getRole(),
            token
    );
}

}