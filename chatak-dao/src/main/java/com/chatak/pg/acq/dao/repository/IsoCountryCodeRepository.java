package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGIsoCountryCode;

public interface IsoCountryCodeRepository extends JpaRepository<PGIsoCountryCode,Long>,QuerydslPredicateExecutor<PGIsoCountryCode>{

}
