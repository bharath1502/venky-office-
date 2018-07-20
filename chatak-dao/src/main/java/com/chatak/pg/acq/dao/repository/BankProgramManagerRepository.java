package com.chatak.pg.acq.dao.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.model.BankProgramManagerMap;

public interface BankProgramManagerRepository extends JpaRepository<BankProgramManagerMap, Long>,
    QueryDslPredicateExecutor<BankProgramManagerMap> {

  public Set<BankProgramManagerMap> findByProgramManagerId(Long programManagerId);

  public Set<BankProgramManagerMap> findByBankId(Long bankId);
  
  	@Modifying
	@Transactional
	@Query("delete from BankProgramManagerMap bankPmMap where bankPmMap.programManagerId = :pmId")
	public void deleteByPmId(@Param("pmId")Long pmId);

}
