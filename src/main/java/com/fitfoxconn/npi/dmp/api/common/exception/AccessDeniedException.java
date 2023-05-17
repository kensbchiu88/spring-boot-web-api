package com.fitfoxconn.npi.dmp.api.common.exception;

/**
 * 共用的Exception，主要用來呈現沒有權限存取的錯誤
 */
public class AccessDeniedException extends RuntimeException{

  public AccessDeniedException() {
    super();
  }

  public AccessDeniedException(String message) {
    super(message);
  }
}
