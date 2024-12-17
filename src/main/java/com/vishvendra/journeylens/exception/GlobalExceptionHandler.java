package com.vishvendra.journeylens.exception;

import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Response> handleBadCredentialsException(BadCredentialsException ex) {
    log.error("BadCredentialsException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("Username/password does not match, unauthorised access.")
        .build();
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Response> handleRuntimeExceptionException(RuntimeException ex) {
    log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("An unexpected error occurred. Please try again later.")
        .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex) {
    log.error("ValidationException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("Validation failed")
        .build();
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    log.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("Method Not Allowed")
        .build();
  }

  @ExceptionHandler(PSQLException.class)
  public ResponseEntity<Response> handlePSQLException(PSQLException ex) {
    log.error("PSQLException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("Some error occurred.")
        .build();
  }
}
