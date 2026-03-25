package com.aurava.aurava_backend.DTO;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "Email or mobile is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}