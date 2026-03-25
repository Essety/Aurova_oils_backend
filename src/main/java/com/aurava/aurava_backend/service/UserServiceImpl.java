package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.LoginRequest;
import com.aurava.aurava_backend.DTO.RegisterRequest;
import com.aurava.aurava_backend.exception.*;
import com.aurava.aurava_backend.entity.User;
import com.aurava.aurava_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // REGISTER
    public User register(RegisterRequest request){

         if (userRepository.existsByEmail(request.getEmail())) {
        throw new EmailAlreadyExistsException();
    }

    if (userRepository.existsByMobile(request.getMobile())) {
    throw new MobileAlreadyExistsException();
}

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        return userRepository.save(user);
    }

    // LOGIN
public User login(LoginRequest request) {

    String username = request.getUsername(); // email or mobile
    User user;

    if(username.contains("@")) {
        // treat as email
        if(!username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidCredentialsException("Invalid email format");
        }
        user = userRepository.findByEmail(username)
                .orElseThrow(InvalidCredentialsException::new);
    } else {
        // treat as mobile
        if(!username.matches("^\\d{10}$")) {
            throw new InvalidCredentialsException("Invalid mobile number format");
        }
        user = userRepository.findByMobile(username)
                .orElseThrow(InvalidCredentialsException::new);
    }

    if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new InvalidCredentialsException();
    }

    return user;
}
}