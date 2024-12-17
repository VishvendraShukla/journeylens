package com.vishvendra.journeylens.exception.analytics;

import com.vishvendra.journeylens.exception.GlobalApplicationException;

public class ProcessorNotFoundException extends GlobalApplicationException {

  public ProcessorNotFoundException(String message) {
    super(message);
  }

}
