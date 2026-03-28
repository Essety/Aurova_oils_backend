package com.aurava.aurava_backend.DTO;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ChangePasswordRequest {
     
    @NotBlank(message = "Old password is required")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;
}