package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.CategeoryModule;

public interface CategeoryModuleRepository
		extends JpaRepository<CategeoryModule, Long>, QueryDslPredicateExecutor<CategeoryModule> {

	List<CategeoryModule> findByCategeoryId(Long categeoryId);

	CategeoryModule findByCategeoryIdAndModuleId(Long categeoryId, Long moduleId);

}