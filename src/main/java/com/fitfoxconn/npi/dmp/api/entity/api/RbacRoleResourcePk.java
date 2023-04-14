package com.fitfoxconn.npi.dmp.api.entity.api;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * role_resource table PK
 */
@Embeddable
@Data
public class RbacRoleResourcePk {
  /**
   * serialVersionUID
   **/
  private static final long serialVersionUID = 1L;

  /** 使用者ID */
  private int roleId;

  /** role ID*/
  private int resourceId;
}
