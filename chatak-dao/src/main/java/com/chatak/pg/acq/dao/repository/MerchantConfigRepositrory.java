package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantConfig;

public interface MerchantConfigRepositrory extends JpaRepository<PGMerchantConfig, Long>,
QueryDslPredicateExecutor<PGMerchantConfig> {

	PGMerchantConfig findById(Long merchantConfigId);

	/**
	 * @param feeName
	 * @return
	 */
	List<PGMerchantConfig> findByFeeProgram(String feeName);

}
