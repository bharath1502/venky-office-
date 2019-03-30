package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.FaqManagement;


public interface FaqManagementRepository extends JpaRepository<FaqManagement, Long>, QueryDslPredicateExecutor<FaqManagement> {

	public FaqManagement findByFaqId(Long faqId);
	

}