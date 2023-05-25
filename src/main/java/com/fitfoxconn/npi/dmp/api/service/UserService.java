package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.common.exception.ValidationException;
import com.fitfoxconn.npi.dmp.api.entity.auth.UserProfileEntity;
import com.fitfoxconn.npi.dmp.api.repository.auth.UserProfileDao;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 使用者管理相關 service
 */
@Service
@Validated
public class UserService {

  @Autowired
  private UserProfileDao userProfileDao;

  /**
   * 修改密碼
   */
  public void changePassword(@NotBlank String username, @NotBlank String oldPassword,
      @NotBlank String newPassword,
      @NotBlank String confirmedNewPassword) throws ValidationException {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!newPassword.equals(confirmedNewPassword)) {
      throw new ValidationException("新密碼不符");
    }

    Optional<UserProfileEntity> userOptional = this.userProfileDao.findByUsername(username);
    if (userOptional.isEmpty()) {
      throw new ValidationException("查無使用者:" + username);
    }
    UserProfileEntity user = userOptional.get();


    if(!passwordEncoder.matches(oldPassword, user.getPassword())){
      throw new ValidationException("密碼不正確");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    this.userProfileDao.save(user);
  }
}
