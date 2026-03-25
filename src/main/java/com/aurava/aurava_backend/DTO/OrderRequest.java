package com.aurava.aurava_backend.DTO;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class OrderRequest {
     
    @NotBlank(message = "Address is required")
    @Size(min = 10, message = "Address must be detailed")
    private String address;

}