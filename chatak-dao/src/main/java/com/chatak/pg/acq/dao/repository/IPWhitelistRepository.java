package com.chatak.pg.acq.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGIPWhitelist;

/**
 * 
 * @author Girmiti
 *
 */
public interface IPWhitelistRepository extends
                                      JpaRepository<PGIPWhitelist, Long>,
                                      QuerydslPredicateExecutor<PGIPWhitelist> {

  public Optional<PGIPWhitelist> findById(Long id);
}
