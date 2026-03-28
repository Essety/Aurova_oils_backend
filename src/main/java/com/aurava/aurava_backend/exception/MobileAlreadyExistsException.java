package com.aurava.aurava_backend.exception;

public class MobileAlreadyExistsException extends RuntimeException {

    public MobileAlreadyExistsException() {
        super("Mobile number is already registered");
    }
}