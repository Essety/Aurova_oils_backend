package com.aurava.aurava_backend.exception;

public class InvalidCredentialsException extends RuntimeException {

    // Default message
    public InvalidCredentialsException() {
        super("Invalid credentials"); // more generic for email or mobile
    }

    // Custom message
    public InvalidCredentialsException(String message) {
        super(message);
    }
}