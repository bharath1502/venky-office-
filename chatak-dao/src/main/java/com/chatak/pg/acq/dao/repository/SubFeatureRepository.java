package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGSubFeature;

public interface SubFeatureRepository  extends JpaRepository<PGSubFeature, Long>,QuerydslPredicateExecutor<PGSubFeature>
{
  public PGSubFeature findBySubFeatureId(Long subFeatureId);
}
