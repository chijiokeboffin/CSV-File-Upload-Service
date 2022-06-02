package com.league.backendchallenge.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
@ResponseStatus
public class RestReponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyFileException.class)
    public final ResponseEntity<ExceptionResponse> emptyFileException(EmptyFileException ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public  final  ResponseEntity<ExceptionResponse> invalidFileFormatException(InvalidFileFormatException ex,
                                                                                WebRequest request){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> fileNotFoundException(DataNotFoundException ex, WebRequest request){

        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public  final  ResponseEntity<ExceptionResponse> fileNotFoundException(FileNotFoundException ex,
                                                                           WebRequest request){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
}
