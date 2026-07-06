package com.example.basicboard.exception;


public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException (String message){
        super(message);
    }
}
