package com.rainer.highlow.exception;


import lombok.Data;

@Data
public class ErrorMessage {
    private String message;
    private int statusCode;
}
