package com.transfer.exception;

import com.transfer.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(BalanceInvalidException.class)
    public ResponseEntity<ErrorDto> handleBalanceInvalidException(AccountInvalidException accountInvalidException){
        logger.error("handleBalanceInvalidException");

        var errorDto = new ErrorDto(
                LocalDateTime.now(),
                accountInvalidException.getMessage()
        );

        return new ResponseEntity<>(errorDto, accountInvalidException.getHttpStatus());
    }

    @ExceptionHandler(AccountInvalidException.class)
    public ResponseEntity<ErrorDto> handleAccountInvalidException(AccountInvalidException accountInvalidException){
        logger.error("handleAccountInvalidException");

        var errorDto = new ErrorDto(
                LocalDateTime.now(),
                accountInvalidException.getMessage()
        );

        return new ResponseEntity<>(errorDto, accountInvalidException.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException){
        logger.error("handleHttpMessageNotReadableException");

        var errorDto = new ErrorDto(
                LocalDateTime.now(),
                httpMessageNotReadableException.getMessage()
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        logger.error("handleException: " + exception.getMessage());

        var exceptionError = new ErrorDto(
                LocalDateTime.now(),
                exception.getMessage()
        );

        return new ResponseEntity<>(exceptionError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
