package com.transfer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BalanceInvalidException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public BalanceInvalidException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
