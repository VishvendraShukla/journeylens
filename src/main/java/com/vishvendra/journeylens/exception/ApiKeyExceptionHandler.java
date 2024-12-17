package com.vishvendra.journeylens.exception;

import com.vishvendra.journeylens.exception.apikey.ApiKeyNotFoundException;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiKeyExceptionHandler {

  @ExceptionHandler(ApiKeyNotFoundException.class)
  public ResponseEntity<Response> handleAppUserNotFoundException(ApiKeyNotFoundException ex) {
    log.error("ApiKeyNotFoundException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("An unexpected error occurred. Please try again later.")
        .build();
  }

}
