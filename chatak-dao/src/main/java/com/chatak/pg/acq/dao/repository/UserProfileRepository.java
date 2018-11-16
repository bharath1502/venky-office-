package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGUserProfile;

public interface UserProfileRepository extends JpaRepository<PGUserProfile, Long>,
QuerydslPredicateExecutor<PGUserProfile>{
	
	  public List<PGUserProfile> findByProfileId(Long userProfileId);
	  
	  public List<PGUserProfile> findByEmailAndPassword(String email, String password);

}
