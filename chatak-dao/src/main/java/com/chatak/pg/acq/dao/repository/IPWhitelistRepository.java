package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGIPWhitelist;

/**
 * 
 * @author Girmiti
 *
 */
public interface IPWhitelistRepository extends
                                      JpaRepository<PGIPWhitelist, Long>,
                                      QueryDslPredicateExecutor<PGIPWhitelist> {

  public List<PGIPWhitelist> findById(Long id);
}
