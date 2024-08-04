package com.bm.transfer.account.handle;

import com.bm.transfer.account.exception.AccountNotFoundException;
import com.bm.transfer.account.exception.InsufficientBalanceException;
import com.bm.transfer.account.exception.PasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandlerFavorite {



    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException e) {

        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errors));
    }


    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(
            PasswordException e
    ) {

        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(errors));
    }


    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(
            InsufficientBalanceException e
    ) {

        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(errors));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors()
                .forEach(error ->{
                    var fieldName = ((FieldError)error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);

                });
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
