package com.fitfoxconn.npi.dmp.api.common;

public class AccessDeniedException extends RuntimeException{

  public AccessDeniedException() {
    super();
  }

  public AccessDeniedException(String message) {
    super(message);
  }
}
