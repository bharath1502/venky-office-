package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGAdminUser;


public interface ProfileRepository extends JpaRepository<PGAdminUser,Long>,QueryDslPredicateExecutor<PGAdminUser> {
	
	public List<PGAdminUser> findByUserName(String userName);
	
	public List<PGAdminUser> findByadminUserId(Long adminUserId);

}
