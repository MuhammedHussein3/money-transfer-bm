package com.example.day1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppException {

    @ExceptionHandler(RecordNotUniqueException.class)
    public ResponseEntity<?> handleRecordNotUnique(RecordNotUniqueException ex) {
        ErrorResponse errorResponse = ErrorResponse.build(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                false,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleRecordNotFound(RecordNotFoundException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        ErrorResponse errorResponse = ErrorResponse.build(HttpStatus.NOT_FOUND,
                ex.getMessage(),
                false,
                LocalDateTime.now());
        errorMap.put("error", errorResponse);
        return errorMap;
    }
}
