package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGAid;

public interface ApplicationIdDetailsRepository extends JpaRepository<PGAid,Long>,QueryDslPredicateExecutor<PGAid>{
	
	public List<PGAid> findByApplicationId(Long applicationId);

}
