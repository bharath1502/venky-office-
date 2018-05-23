package com.chatak.pg.acq.dao.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.BankPartnerMap;

public interface BankPartnerRepository
    extends JpaRepository<BankPartnerMap, Long>, QueryDslPredicateExecutor<BankPartnerMap> {

  public Set<BankPartnerMap> findByPartnerId(Long partnerId);

}
