package com.chatak.pg.acq.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGMerchantConfig;

public interface MerchantConfigRepositrory extends JpaRepository<PGMerchantConfig, Long>,
QuerydslPredicateExecutor<PGMerchantConfig> {

	Optional<PGMerchantConfig> findById(Long merchantConfigId);

	/**
	 * @param feeName
	 * @return
	 */
	List<PGMerchantConfig> findByFeeProgram(String feeName);

}
