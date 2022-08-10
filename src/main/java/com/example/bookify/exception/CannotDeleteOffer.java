package com.example.bookify.exception;

public class CannotDeleteOffer extends RuntimeException {

    public CannotDeleteOffer(String message) {
        super(message);
    }
}
