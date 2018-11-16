package com.chatak.pg.acq.dao.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.ProgramManagerAccount;

public interface ProgramManagerAccountRepository extends JpaRepository<ProgramManagerAccount, Long>,
    QuerydslPredicateExecutor<ProgramManagerAccount> {

  public List<ProgramManagerAccount> findByProgramManagerId(Long programManagerId);

  public ProgramManagerAccount findByProgramManagerIdAndAccountNumber(Long pmId,
      Long accountNumber);

  public List<ProgramManagerAccount> findByAccountNumber(Long accountNumber);

  public ProgramManagerAccount findByIdAndAccountType(Long id, String accountType);

  public Optional<ProgramManagerAccount> findById(Long id);

  public ProgramManagerAccount findByProgramManagerIdAndAccountType(Long programManagerId,
      String accountType);

  @Modifying
  @Query("UPDATE ProgramManagerAccount pm" + " SET pm.status = :status,"
      + " pm.updatedDate=:updatedDate," + " pm.updatedBy=:updatedBy"
      + " WHERE pm.programManagerId=:id")
  public void changeStatus(@Param("status") String status,
      @Param("updatedDate") Timestamp updatedDate, @Param("updatedBy") String updatedBy,
      @Param("id") Long id);

}
