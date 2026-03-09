package com.codingShuttle.SecurityApp.SecurityApplication.advices;

import com.codingShuttle.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError=new ApiError( HttpStatus.NOT_FOUND,exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
     public ResponseEntity<ApiError> hangdleAuthenticationException(AuthenticationException ex){
        ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex){
        ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }
}
