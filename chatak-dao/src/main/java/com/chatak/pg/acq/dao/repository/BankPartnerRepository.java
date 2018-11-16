package com.chatak.pg.acq.dao.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.BankPartnerMap;

public interface BankPartnerRepository
    extends JpaRepository<BankPartnerMap, Long>, QuerydslPredicateExecutor<BankPartnerMap> {

  public Set<BankPartnerMap> findByPartnerId(Long partnerId);

}
