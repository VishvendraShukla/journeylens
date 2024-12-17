package com.vishvendra.journeylens.exception;

import com.vishvendra.journeylens.exception.appuser.AppUserNotFoundException;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AppUserExceptionHandler {

  @ExceptionHandler(AppUserNotFoundException.class)
  public ResponseEntity<Response> handleAppUserNotFoundException(AppUserNotFoundException ex) {
    log.error("AppUserNotFoundException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withMessage("An unexpected error occurred. Please try again later.")
        .build();
  }
}
