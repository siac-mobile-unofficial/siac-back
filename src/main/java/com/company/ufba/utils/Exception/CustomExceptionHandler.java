package com.company.ufba.utils.Exception;

import com.company.ufba.utils.Exception.custom.LoginException;
import com.company.ufba.utils.Exception.custom.RegisterException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {RegisterException.class})
    public ResponseEntity<Object> registerError(RegisterException ex){
        Logger.getLogger("Register").info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND,ex));
    }

    @ExceptionHandler(value = { LoginException.class})
    public ResponseEntity<Object> loginError(LoginException ex) {
        Logger.getLogger("Login").info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildErrorResponse(HttpStatus.UNAUTHORIZED,ex));
    }
    private static ErrorResponse buildErrorResponse(final HttpStatus status, final RuntimeException ex) {
        return new ErrorResponse(status.value(), ex.getMessage());
    }
}