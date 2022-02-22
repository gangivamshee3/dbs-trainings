package com.classpath.ordermicroservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Set<Error> handleInvalidInput(MethodArgumentNotValidException exception){
        log.error("Exception in input");
        return exception.getAllErrors()
                        .stream()
                            .map(ObjectError::getDefaultMessage)
                            .map(message -> new Error(100, message))
                        .collect(Collectors.toSet());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidInput(IllegalArgumentException exception){
        log.error("Exception in input");
        return new Error(100, exception.getMessage());
    }
}

@AllArgsConstructor
@Getter
class Error {
    private int code;
    private String message;
}