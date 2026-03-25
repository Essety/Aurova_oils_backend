package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.DTO.*;
import com.aurava.aurava_backend.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ProfileResponse getProfile(){
        return profileService.getProfile();
    }

    @PutMapping
    public ProfileResponse updateProfile(@Valid @RequestBody UpdateProfileRequest request){
        return profileService.updateProfile(request);
    }

    @PutMapping("/password")
    public String changePassword(@Valid @RequestBody ChangePasswordRequest request){
        profileService.changePassword(request);
        return "Password updated successfully";
    }
}