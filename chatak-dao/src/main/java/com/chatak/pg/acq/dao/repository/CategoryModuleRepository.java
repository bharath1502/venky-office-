package com.chatak.pg.acq.dao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.CategoryModule;


public interface CategoryModuleRepository
		extends JpaRepository<CategoryModule, Long>, QueryDslPredicateExecutor<CategoryModule> {

	List<CategoryModule> findByCategoryId(Long categoryId);

	CategoryModule findByCategoryIdAndModuleId(Long categoryId, Long moduleId);

}