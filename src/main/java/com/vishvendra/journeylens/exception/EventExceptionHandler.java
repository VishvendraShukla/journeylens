package com.vishvendra.journeylens.exception;

import com.vishvendra.journeylens.exception.event.EventRequestMalformedException;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class EventExceptionHandler {

  @ExceptionHandler(EventRequestMalformedException.class)
  public ResponseEntity<Response> handleBadCredentialsException(EventRequestMalformedException ex) {
    log.error("EventRequestMalformedException: {}", ex.getMessage());
    return ApiResponseSerializer.errorResponseSerializerBuilder()
        .withStatusCode(HttpStatus.BAD_REQUEST)
        .withMessage("Request rejected. Reason: Malformed.")
        .build();
  }

}
