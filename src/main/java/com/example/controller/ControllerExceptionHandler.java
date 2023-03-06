package com.example.controller;

import com.example.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ItemAlreadyExistException.class, ItemNotFoundException.class,
            WrongArgumentException.class, ProfileCreateException.class, CommentCreateException.class,
            ContactCreateException.class, PostCreateException.class})
    public ResponseEntity<?> handle(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
