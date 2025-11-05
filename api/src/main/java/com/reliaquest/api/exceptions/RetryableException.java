package com.reliaquest.api.exceptions;

public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }
}
