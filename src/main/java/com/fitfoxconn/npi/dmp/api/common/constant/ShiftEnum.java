package com.fitfoxconn.npi.dmp.api.common.constant;

import com.fitfoxconn.npi.dmp.api.common.exception.ValidationException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 班別Enum: 1:早班, 2:晚班
 */
@AllArgsConstructor
@Getter
public enum ShiftEnum {
  MORNING(1),
  NIGHT(2);

  private int code;

  /**
   * 依照班別代碼取得ShiftEnum
   *
   * @param code 班別代碼
   */
  public static ShiftEnum getByShiftCode(int code) throws ValidationException {
    return Arrays.asList(values()).stream().filter(e -> code == e.code).findFirst()
        .orElseThrow(() -> new ValidationException("班別代碼錯誤:" + code));
  }
}
