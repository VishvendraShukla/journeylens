package com.vishvendra.journeylens.exception.apikey;

import com.vishvendra.journeylens.exception.GlobalApplicationException;

public class ApiKeyNotFoundException extends GlobalApplicationException {

  public ApiKeyNotFoundException(String message) {
    super(message);
  }

}
