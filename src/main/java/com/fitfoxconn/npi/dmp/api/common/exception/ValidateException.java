package com.fitfoxconn.npi.dmp.api.common.exception;

/**
 * 共用的Exception，主要用來呈現預期中的檢核錯誤
 */
public class ValidateException extends Exception{

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  public ValidateException() {
    super();
  }

  public ValidateException(String message) {
    super(message);
  }
}
