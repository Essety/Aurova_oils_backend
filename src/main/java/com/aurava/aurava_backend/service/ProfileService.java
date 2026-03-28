package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.*;

public interface ProfileService {

    ProfileResponse getProfile();

    ProfileResponse updateProfile(UpdateProfileRequest request);

    void changePassword(ChangePasswordRequest request);
}