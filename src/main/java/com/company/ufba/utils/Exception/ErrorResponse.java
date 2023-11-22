package com.company.ufba.utils.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.extern.flogger.Flogger;
import org.springframework.http.HttpStatus;
@NoArgsConstructor @Getter
public class ErrorResponse {
    private int status;
    private String message;
    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }
}
