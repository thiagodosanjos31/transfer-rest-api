package com.transfer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AccountInvalidException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public AccountInvalidException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
