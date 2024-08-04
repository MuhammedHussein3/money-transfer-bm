package com.example.day1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private Boolean success;
    private LocalDateTime timestamp;

}
