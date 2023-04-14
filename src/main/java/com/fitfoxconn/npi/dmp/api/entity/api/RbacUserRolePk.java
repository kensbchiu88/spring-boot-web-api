package com.fitfoxconn.npi.dmp.api.entity.api;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

/**
 * user_role table PK
 */
@Embeddable
@Data
public class RbacUserRolePk implements Serializable {

  /**
   * serialVersionUID
   **/
  private static final long serialVersionUID = 1L;

  /** 使用者ID */
  private int userId;

  /** role ID*/
  private int roleId;
}
