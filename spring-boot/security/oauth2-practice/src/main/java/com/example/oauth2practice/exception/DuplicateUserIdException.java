package com.example.oauth2practice.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(String message) {

        super(message);
    }
}
