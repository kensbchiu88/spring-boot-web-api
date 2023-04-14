package com.fitfoxconn.npi.dmp.api.entity.api;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_resource", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RbacRoleResourceEntity {

  @EmbeddedId
  private RbacRoleResourcePk id;
  /** id in role table */

  @Column(name = "role_id", nullable = false)
  private int roleId;

  /** id in resource table */
  @Column(name = "resource_id", nullable = false)
  private int resourceId;
}
