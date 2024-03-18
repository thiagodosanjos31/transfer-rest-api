package com.transfer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TransferValueHigherThanPermitted extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;

    public TransferValueHigherThanPermitted(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
