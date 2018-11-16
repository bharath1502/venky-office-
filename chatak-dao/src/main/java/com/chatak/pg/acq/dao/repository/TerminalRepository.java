package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGTerminal;

/**
 * DAO Repository class to process Terminals
 * 
 * @author Girmiti Software
 * @date 08-Dec-2014 12:33:27 pm
 * @version 1.0
 */
public interface TerminalRepository extends JpaRepository<PGTerminal, Long>, QuerydslPredicateExecutor<PGTerminal> {

  public List<PGTerminal> findByTerminalIdAndMerchantId(Long terminalId, Long merchantId);

  public PGTerminal findByTerminalId(Long terminalId);

  public PGTerminal findByMerchantId(Long merchantId);

}
