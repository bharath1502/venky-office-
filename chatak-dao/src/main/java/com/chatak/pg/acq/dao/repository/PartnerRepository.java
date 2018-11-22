package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.Partner;

public interface PartnerRepository
    extends JpaRepository<Partner, Long>, QueryDslPredicateExecutor<Partner> {

  public List<Partner> getDetailsByPartnerId(Long partnerId);

  public List<Partner> getDetailsByProgramManagerIdAndPartnerId(Long programManagerId,
      Long partnerId);

  @Modifying
  @Query("UPDATE Partner p" + " SET p.status = :status," + " p.reason = :reason,"
      + " p.updatedDate=:updatedDate," + " p.updatedBy=:updatedBy"
      + " WHERE p.partnerId=:partnerId")
  public void changeStatus(@Param("status") String status, @Param("reason") String reason,
      @Param("updatedDate") Timestamp updatedDate, @Param("updatedBy") String updatedBy,
      @Param("partnerId") Long partnerId);

  public Partner findByPartnerId(Long partnerId);


  public List<Partner> findByProgramManagerId(Long programManagerId);
  
  public List<Partner> findByStatus(String status);

}
