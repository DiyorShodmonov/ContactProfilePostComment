package com.example.exceptions;

public class PostCreateException extends RuntimeException {
    public PostCreateException(String message) {
        super(message);
    }
}
