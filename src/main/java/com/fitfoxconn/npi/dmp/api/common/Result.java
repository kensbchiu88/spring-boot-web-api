package com.fitfoxconn.npi.dmp.api.common;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
@Schema(description = "API固定回傳格式")
public class Result<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "請求結果(true=成功, false=失敗)", example = "true")
  private boolean success;

  @Schema(description = "錯誤訊息")
  private String error;
  @Schema(description = "回傳資料")
  private T data;

  /**
   * 預設建構子
   */
  public Result() {
    super();
  }

  public Result(final T data, final String error, final Boolean success) {
    this.data = data;
    this.error = error;
    this.success = success;
  }

  /**
   * Constructor By Data and Success
   *
   * @param data ResultData
   * @param success Result State
   */
  public Result(final T data, final Boolean success) {
    this.data = data;
    this.success = success;
  }

  /**
   * Constructor By error message
   *
   * @param error error message
   */
  public Result(final String error) {
    this.error = error;
    this.success = Boolean.FALSE;
  }

  /**
   * Return Success Result
   *
   * @param data ResultData
   * @return Result
   */
  public static <T> Result<T> success(final T data) {
    return new Result<>(data, Boolean.TRUE);
  }


  /**
   * Return Fail Result
   *
   * @param errorMessage error message
   * @return Result
   */
  public static Result<?> fail(final String errorMessage) {
    return new Result<>(errorMessage);
  }

}
