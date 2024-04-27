package com.userstorage.exception.global;

import com.userstorage.exception.custome.FileExistsExceptions;
import com.userstorage.exception.custome.FileNotFoundException;
import com.userstorage.exception.custome.FileUploadException;
import com.userstorage.exception.custome.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @PROJECT_NAME : User-Stroage
 * @PACKAGE_NAME : com.userstroage.exception.global
 * @CREATED_BY : nikhilupadhyay
 * @CREATED_DATE : 27/04/24
 **/

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> handleInvalidFieldException(InvalidDataException e) {
        return ResponseEntity.badRequest().body(new ArrayList<>(Collections.singleton(e.getMessage())));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException e) {
        return ResponseEntity.badRequest().body(new ArrayList<>(Collections.singleton(e.getMessage())));
    }
    @ExceptionHandler(FileExistsExceptions.class)
    public ResponseEntity<Object> handleFileExistsException(FileExistsExceptions e) {
        return ResponseEntity.badRequest().body(new ArrayList<>(Collections.singleton(e.getMessage())));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<Object> handleFileUploadException(FileUploadException e) {
        return ResponseEntity.badRequest().body(new ArrayList<>(Collections.singleton(e.getMessage())));
    }

}
