package com.aurava.aurava_backend.exception;

import com.aurava.aurava_backend.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 400 - Email already exists
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmailExists(EmailAlreadyExistsException ex) {
        return new ErrorResponse(400, ex.getMessage());
    }

    // ✅ 401 - Login error
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalid(InvalidCredentialsException ex) {
        return new ErrorResponse(401, ex.getMessage());
    }

    // ✅ 400 - Validation error (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return new ErrorResponse(400, message);
    }

    // ✅ 500 - Any unknown error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse(500, "Something went wrong");
    }

    @ExceptionHandler(MobileAlreadyExistsException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ErrorResponse handleMobileExists(MobileAlreadyExistsException ex) {
    return new ErrorResponse(400, ex.getMessage());
}
}