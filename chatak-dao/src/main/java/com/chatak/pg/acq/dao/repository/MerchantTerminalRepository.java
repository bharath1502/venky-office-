package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantTerminal;

public interface MerchantTerminalRepository extends JpaRepository<PGMerchantTerminal,Long>,QuerydslPredicateExecutor<PGMerchantTerminal> {

	public PGMerchantTerminal findByTerminalId(String terminalId);
	
	public Optional<PGMerchantTerminal> findById(Long id);
	
	public List<PGMerchantTerminal> findByMerchantId(String merchantId);
	
	public PGMerchantTerminal findByMerchantIdAndTerminalId(Long merchantId, String terminalId);

	
}
