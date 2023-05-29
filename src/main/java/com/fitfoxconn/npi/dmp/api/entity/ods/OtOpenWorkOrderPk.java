package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Data;

/**
 * ot_open_work_order table PK
 */
@Embeddable
@Data
public class OtOpenWorkOrderPk {
  /**
   * serialVersionUID
   **/
  private static final long serialVersionUID = 1L;

  /** 登記為未結工單的時間 */
  private LocalDate showDate;
  /** 工單號 */
  private String workNumber;

  /** 裝配件料號 */
  private String assemblyPartNumber;

  /** 組件料號 */
  private String componentPartNumber;
}
