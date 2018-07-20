package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGUserAuthentication;

public interface UserAuthenticationRepository extends JpaRepository<PGUserAuthentication, Long>,
QueryDslPredicateExecutor<PGUserAuthentication>{
	
	  public List<PGUserAuthentication> findByTokenAndClientIP(String token, String clientIP);
	  
	  public List<PGUserAuthentication> findByProfileId(Long userProfileId);

}
