package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.*;
import com.aurava.aurava_backend.entity.User;
import com.aurava.aurava_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getLoggedUser(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public ProfileResponse getProfile() {

        User user = getLoggedUser();

        return ProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }

 @Override
@Transactional
public ProfileResponse updateProfile(UpdateProfileRequest request) {

    User user = getLoggedUser();

    userRepository.findByEmail(request.getEmail())
            .ifPresent(existingUser -> {
                if(!existingUser.getId().equals(user.getId())){
                    throw new RuntimeException("Email already in use");
                }
            });

    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setMobile(request.getMobile());

    userRepository.save(user);

    // ✅ DO NOT call getProfile()
    return ProfileResponse.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .mobile(user.getMobile())
            .build();
}

    @Override
    public void changePassword(ChangePasswordRequest request) {

        User user = getLoggedUser();

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}