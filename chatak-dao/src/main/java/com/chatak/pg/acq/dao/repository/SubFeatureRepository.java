package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGSubFeature;

public interface SubFeatureRepository  extends JpaRepository<PGSubFeature, Long>,QueryDslPredicateExecutor<PGSubFeature>
{
  public PGSubFeature findBySubFeatureId(Long subFeatureId);
}
