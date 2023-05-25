package com.fitfoxconn.npi.dmp.api.repository.auth;

import com.fitfoxconn.npi.dmp.api.entity.api.AccessLogEntity;
import com.fitfoxconn.npi.dmp.api.entity.auth.UserProfileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** UserProfile dao */
@Repository
public interface UserProfileDao extends JpaRepository<UserProfileEntity, Integer> {

  /**
   * 查詢UserProfile
   *
   * @param username 使用者名稱
   *
   * @return UserProfileEntity
   */
  public Optional<UserProfileEntity> findByUsername(String username);

}
