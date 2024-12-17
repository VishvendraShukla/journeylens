package com.vishvendra.journeylens.exception.appuser;

import com.vishvendra.journeylens.exception.GlobalApplicationException;

public class AppUserNotFoundException extends GlobalApplicationException {

  public AppUserNotFoundException(String message) {
    super(message);
  }

}
