package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeatures;


public interface FeatureRepository extends JpaRepository<PGFeatures, Long>,QueryDslPredicateExecutor<PGFeatures>
{
	
	public PGFeatures findByFeatureId(Long featureId);

}
