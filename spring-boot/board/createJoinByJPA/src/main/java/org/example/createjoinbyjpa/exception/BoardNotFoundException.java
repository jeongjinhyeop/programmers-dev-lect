package org.example.createjoinbyjpa.exception;

public class BoardNotFoundException  extends RuntimeException{
    public BoardNotFoundException(String message){
        super(message);
    }
}
