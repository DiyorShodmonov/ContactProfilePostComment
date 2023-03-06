package com.example.exceptions;

public class CommentCreateException extends RuntimeException{
    public CommentCreateException(String message) {
        super(message);
    }
}
