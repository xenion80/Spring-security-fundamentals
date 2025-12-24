package com.codingShuttle.SecurityApp.SecurityApplication.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String error;
    private HttpStatus statuscode;

    public ApiError(HttpStatus statuscode,String error) {
        this();
        this.statuscode = statuscode;
        this.error=error;
    }

    public ApiError() {
        this.timestamp=LocalDateTime.now();
    }
}
