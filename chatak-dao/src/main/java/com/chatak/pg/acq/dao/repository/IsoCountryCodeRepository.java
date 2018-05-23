package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGIsoCountryCode;

public interface IsoCountryCodeRepository extends JpaRepository<PGIsoCountryCode,Long>,QueryDslPredicateExecutor<PGIsoCountryCode>{

}
