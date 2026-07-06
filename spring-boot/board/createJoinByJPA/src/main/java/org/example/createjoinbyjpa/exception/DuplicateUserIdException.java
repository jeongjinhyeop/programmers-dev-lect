package org.example.createjoinbyjpa.exception;

public class DuplicateUserIdException extends RuntimeException{
    public DuplicateUserIdException (String message){
        super(message);
    }
}
