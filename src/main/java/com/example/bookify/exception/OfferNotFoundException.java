package com.example.bookify.exception;

public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException(String message) {
        super(message);
    }
}
