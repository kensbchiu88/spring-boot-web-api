package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.OrganizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizeDao extends JpaRepository<OrganizeEntity, Integer> {
    public List<OrganizeEntity> findByFatherId(int fatherId);
}
