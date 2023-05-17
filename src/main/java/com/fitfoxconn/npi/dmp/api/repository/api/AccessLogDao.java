package com.fitfoxconn.npi.dmp.api.repository.api;

import com.fitfoxconn.npi.dmp.api.entity.api.AccessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** AccessLog dao */
@Repository
public interface AccessLogDao extends JpaRepository<AccessLogEntity, Long> {
}
