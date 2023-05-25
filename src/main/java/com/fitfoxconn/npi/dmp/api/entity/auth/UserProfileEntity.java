package com.fitfoxconn.npi.dmp.api.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile", schema = "auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileEntity {
  /** id 流水號*/
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "serial", nullable = false)
  private int id;

  /** username */
  @Column(name = "username")
  private String username;

  /** password */
  @Column(name = "password")
  private String password;

  /** email */
  @Column(name = "email")
  private String email;

  /** role */
  @Column(name = "role")
  private String role;

  /** 使用者工號 */
  @Column(name = "worker_id")
  private String workerId;

  /** 所屬部門 */
  @Column(name = "department")
  private String department;

  /** 帳號新增原因 */
  @Column(name = "reason")
  private String reason;

  /** password */
  @Column(name = "is_enable")
  private boolean isEnable;

  /** 新增時間 */
  @Column(name = "create_time")
  private LocalDateTime createTime;

  /** 新增者 */
  @Column(name = "creator")
  private String creator;

  /** 修改時間 */
  @Column(name = "update_time")
  private LocalDateTime updateTime;

  /** 修改者 */
  @Column(name = "updater")
  private String updater;

}
