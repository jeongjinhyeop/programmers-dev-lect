package com.example.security.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(String message) {

      super(message);
    }
}
