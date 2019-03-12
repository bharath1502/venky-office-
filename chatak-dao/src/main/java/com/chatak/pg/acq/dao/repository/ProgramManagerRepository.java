package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.ProgramManager;

public interface ProgramManagerRepository
    extends JpaRepository<ProgramManager, Long>, QueryDslPredicateExecutor<ProgramManager> {

  @Modifying
  @Query("UPDATE ProgramManager pm" + " SET pm.status = :status," + " pm.reason = :reason,"
      + " pm.updatedDate=:updatedDate," + " pm.updatedBy=:updatedBy" + " WHERE pm.id=:id")
  public void changeStatus(@Param("status") String status, @Param("reason") String reason,
      @Param("updatedDate") Timestamp updatedDate, @Param("updatedBy") String updatedBy,
      @Param("id") Long id);

  @Modifying
  @Query("UPDATE ProgramManager pm" + " SET pm.defaultProgramManager = :defaultValue "
      + " WHERE pm.defaultProgramManager = :defaultProgramManager ")
  public void changeDefaultProgramManager(
      @Param("defaultProgramManager") Boolean defaultProgramManager,
      @Param("defaultValue") Boolean defaultValue);

  public ProgramManager findById(Long id);
  
  public List<ProgramManager> findByAccountCurrencyAndStatusLike(String currencyId, String status);
  
  public List<ProgramManager> findByIssuancepmid(Long issuancePmId);
  
  public List<ProgramManager> findByIdAndAccountCurrency(Long pmId,String currencyId);
  
  @Query("select pm from ProgramManager pm where pm.pmSystemConvertedTime like %:pmSystemConvertedTime%")
  public List<ProgramManager> findByBatchTime(@Param("pmSystemConvertedTime")String pmSystemConvertedTime);
  
//  @Query("select batchPrefix from ProgramManager pm where pm.id = :id")
}
