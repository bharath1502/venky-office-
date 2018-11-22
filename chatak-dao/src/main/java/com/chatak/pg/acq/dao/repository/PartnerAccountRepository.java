package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PartnerAccount;

public interface PartnerAccountRepository
    extends JpaRepository<PartnerAccount, Long>, QueryDslPredicateExecutor<PartnerAccount> {

  public List<PartnerAccount> findByPartnerIdAndAccountType(Long partnerId, String accountType);

  public PartnerAccount findByPartnerAccountId(Long partnerAccountId);

  public PartnerAccount findByPartnerAccountIdAndAccountType(Long partnerAccountId,
      String accountType);

  @Modifying
  @Query("UPDATE PartnerAccount p" + " SET p.status = :status," + " p.updatedDate=:updatedDate,"
      + " p.updatedBy=:updatedBy" + " WHERE p.partnerId=:partnerId")
  public void changeStatus(@Param("status") String status,
      @Param("updatedDate") Timestamp updatedDate, @Param("updatedBy") String updatedBy,
      @Param("partnerId") Long partnerId);

}
