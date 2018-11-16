package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGFeatures;


public interface FeatureRepository extends JpaRepository<PGFeatures, Long>,QuerydslPredicateExecutor<PGFeatures>
{
	
	public PGFeatures findByFeatureId(Long featureId);

}
