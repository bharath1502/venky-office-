package com.chatak.pg.acq.dao.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.BankProgramManagerMap;

public interface BankProgramManagerRepository extends JpaRepository<BankProgramManagerMap, Long>,
    QueryDslPredicateExecutor<BankProgramManagerMap> {

  public Set<BankProgramManagerMap> findByProgramManagerId(Long programManagerId);

  public Set<BankProgramManagerMap> findByBankId(Long bankId);

}
