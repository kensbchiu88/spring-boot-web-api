package com.fitfoxconn.npi.dmp.api.repository.api;

import com.fitfoxconn.npi.dmp.api.entity.api.RbacResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * RbacResource dao
 */
public interface RbacResourceDao extends JpaRepository<RbacResourceEntity, Integer> {

  @Query(value = "select count(1)"
      + "  from RbacResourceEntity r, RbacUserEntity u ,RbacResourceEntity ro, RbacUserRoleEntity ur, RbacRoleResourceEntity rr "
      + "  where u.username = ?1"
      + "    and u.isActive = true"
      + "    and ur.userId = u.id "
      + "    and ro.id = ur.roleId"
      + "    and ro.isActive = true"
      + "    and rr.roleId = ro.id"
      + "    and r.id = rr.resourceId"
      + "    and r.isActive = true"
      + "    and r.resourceName = ?2")
  public int CountActiveResourceByUsernameAndResourceName(String username, String resourceName);

}
