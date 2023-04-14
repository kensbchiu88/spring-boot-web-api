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
@Table(name = "user", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RbacUserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  /** username */
  @Column(name = "username", nullable = false)
  private String username;

  /** password */
  @Column(name = "password")
  private String password;

  /** email */
  @Column(name = "email")
  private String email;

  /** 是否有效 */
  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  /** 建立時間 */
  @Column(name = "create_time", nullable = false)
  private LocalDateTime createTime;
}
