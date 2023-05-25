package com.fitfoxconn.npi.dmp.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fitfoxconn.npi.dmp.api.entity.auth.UserProfileEntity;
import com.fitfoxconn.npi.dmp.api.repository.auth.UserProfileDao;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.fitfoxconn.npi.dmp.api.common.exception.ValidationException;

/**
 * unit test of service.UserService
 */
@ExtendWith(SpringExtension.class)
@Slf4j
class UserServiceTest {

  private UserProfileDao userProfileDao = Mockito.mock(UserProfileDao.class);
  @InjectMocks
  private UserService userService;

  private static final String USERNAME = "username";
  private static final String OLD_PASSWORD = "password";

  @BeforeEach
  public void before() {

  }

  /**
   * 修改密碼測試
   * 測試new password與confirmed new password 不一致
   */
  @Test
  public void testNewPasswordNotSame() {
    when(this.userProfileDao.findByUsername(Mockito.any())).thenReturn(
        Optional.of(this.mockUserProfile()));
    assertThrows(
        ValidationException.class,
        () -> this.userService.changePassword(USERNAME, OLD_PASSWORD, "1", "2"));
  }

  /**
   * 修改密碼測試
   * 測試無此使用者帳號
   */
  @Test
  public void testNoUser() {
    when(this.userProfileDao.findByUsername(Mockito.any())).thenReturn(Optional.empty());
    assertThrows(
        ValidationException.class,
        () -> this.userService.changePassword(USERNAME, OLD_PASSWORD, "1", "1"));
  }

  /**
   * 修改密碼測試
   * 測試密碼錯誤
   */
  @Test
  public void testWrongPassword() {
    when(this.userProfileDao.findByUsername(Mockito.any())).thenReturn(
        Optional.of(this.mockUserProfile()));
    assertThrows(
        ValidationException.class,
        () -> this.userService.changePassword(USERNAME, "wrong_password", "1", "1"));
  }

  private UserProfileEntity mockUserProfile() {
    return UserProfileEntity.builder()
        .username(USERNAME)
        .password(new BCryptPasswordEncoder().encode(OLD_PASSWORD))
        .isEnable(true).build();
  }

}