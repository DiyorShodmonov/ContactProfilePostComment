package com.example.exceptions;

public class ContactCreateException extends RuntimeException{
    public ContactCreateException(String message) {
        super(message);
    }
}
