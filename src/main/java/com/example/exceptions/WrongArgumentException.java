package com.example.exceptions;

public class WrongArgumentException extends RuntimeException{
    public WrongArgumentException(String message) {
        super(message);
    }
}
