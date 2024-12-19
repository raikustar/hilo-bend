package com.rainer.highlow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(Exception ex){
        ErrorMessage message = new ErrorMessage();
        message.setStatusCode(400);
        message.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(message);
    }
}
