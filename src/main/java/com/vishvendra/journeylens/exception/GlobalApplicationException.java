package com.vishvendra.journeylens.exception;

public class GlobalApplicationException extends RuntimeException {

  public GlobalApplicationException() {
  }

  public GlobalApplicationException(String message) {
    super(message);
  }

  public GlobalApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

  public GlobalApplicationException(Throwable cause) {
    super(cause);
  }

  public GlobalApplicationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
