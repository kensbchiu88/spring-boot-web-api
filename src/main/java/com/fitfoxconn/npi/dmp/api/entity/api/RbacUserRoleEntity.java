package com.fitfoxconn.npi.dmp.api.entity.api;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RbacUserRoleEntity {

  @EmbeddedId
  private RbacUserRolePk id;

  /** id in user table */
  @Column(name = "user_id", nullable = false)
  private int userId;

  /** id in role table */
  @Column(name = "role_id", nullable = false)
  private int roleId;
}
