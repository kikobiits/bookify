package com.example.bookify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidDatesException extends RuntimeException {

    public InvalidDatesException(String message) {
        super(message);
    }
}
