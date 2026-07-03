package com.example.taskmaneger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(NotFoundException.class) //class volame lebo reprezentuje objekt,
    // ukazuje ze no usages, ale pozuiva sa riesi to Spring kvoli tej anotacie
    public ResponseEntity<String> handleNotFound(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());  //nastavime status na 404, a spravu z vynimky
    }

}
