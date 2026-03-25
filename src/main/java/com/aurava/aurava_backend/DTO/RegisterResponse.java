package com.aurava.aurava_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String mobile;
    private String role;
}
