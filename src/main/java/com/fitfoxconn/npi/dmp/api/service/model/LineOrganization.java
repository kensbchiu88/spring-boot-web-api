package com.fitfoxconn.npi.dmp.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 產線所屬組織的資訊 (function > produce > line)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineOrganization {
  /**
   * 所屬line的組織碼
   */
  private String lineOrganizationCode;
  /**
   * 所屬line的名稱
   */
  private String lineName;
  /**
   * 所屬產品的組織碼
   */
  private String productOrganizationCode;
  /**
   * 所屬產品的名稱
   */
  private String productName;
  /**
   * 所屬function的組織碼
   */
  private String functionOrganizationCode;
  /**
   * 所屬function的名稱
   */
  private String functionName;
}
