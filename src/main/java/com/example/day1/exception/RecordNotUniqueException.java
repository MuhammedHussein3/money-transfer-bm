package com.example.day1.exception;

public class RecordNotUniqueException extends RuntimeException{
    public RecordNotUniqueException() {
        super();
    }
    public RecordNotUniqueException(String message) {
        super(message);
    }
}
