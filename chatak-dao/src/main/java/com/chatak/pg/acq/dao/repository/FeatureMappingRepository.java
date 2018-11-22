package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;

public interface FeatureMappingRepository extends JpaRepository<PGRolesFeatureMapping, Long>,QueryDslPredicateExecutor<PGRolesFeatureMapping>
{
  public List<PGRolesFeatureMapping> findByRoleId(Long roleId);
}
