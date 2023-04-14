package com.fitfoxconn.npi.dmp.api.entity.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RbacRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  /** resource name */
  @Column(name = "role_name", nullable = false)
  private String roleName;

  /** description */
  @Column(name = "description")
  private String description;

  /** 是否有效 */
  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  /** 建立時間 */
  @Column(name = "create_time", nullable = false)
  private LocalDateTime createTime;
}
