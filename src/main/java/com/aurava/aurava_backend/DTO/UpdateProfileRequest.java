package com.aurava.aurava_backend.DTO;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Mobile must be 10 digits"
    )
    private String mobile;
}