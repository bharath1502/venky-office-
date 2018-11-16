package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;



public interface RolesFeatureMappingRepository  extends JpaRepository<PGRolesFeatureMapping, Long>,QuerydslPredicateExecutor<PGRolesFeatureMapping> 
{
  public List<PGRolesFeatureMapping> findByRoleId(Long roleId);
}
