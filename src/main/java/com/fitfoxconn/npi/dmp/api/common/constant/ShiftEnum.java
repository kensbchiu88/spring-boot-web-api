package com.fitfoxconn.npi.dmp.api.common.constant;

import com.fitfoxconn.npi.dmp.api.common.exception.ValidateException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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
  public static ShiftEnum getByShiftCode(int code) throws ValidateException {
    return Arrays.asList(values()).stream().filter(e -> code == e.code).findFirst()
        .orElseThrow(() -> new ValidateException("班別代碼錯誤:" + code));
  }
}
