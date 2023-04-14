package com.fitfoxconn.npi.dmp.api.config;

import com.fitfoxconn.npi.dmp.api.common.Result;
import java.lang.reflect.UndeclaredThrowableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.fitfoxconn.npi.dmp.api")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

  /**
   * Exception Handler
   *
   * @param ex Exception
   * @return Object
   */
  @ExceptionHandler(Exception.class)
  public Object handleException(final Exception ex) {
    this.logError(ex);
    return this.throwSystemError(ex.getMessage());
  }

  /**
   * UndeclaredThrowableException Handler
   *
   * @param ex UndeclaredThrowableException
   * @return Object
   */
  @ExceptionHandler(UndeclaredThrowableException.class)
  public Object handleUndeclaredThrowableException(final UndeclaredThrowableException ex) {
    Result<?> result = null;
    System.out.println("##########UndeclaredThrowableException: " + ex.getMessage());
    result = this.throwSystemError(ex.getMessage());

    return result;
  }

  /**
   * 共用系統錯誤，避免Exception內容直接拋出
   *
   * @return Result<?>
   */
  private Result<?> throwSystemError(String errorMessage) {
    return Result.fail(errorMessage);
  }

  /**
   * 顯示 Exception message
   *
   * @param ex Exception
   */
  private void logError(Exception ex) {
    log.error("message:" + ex.getMessage(), ex);
  }

}
