package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.CategoryModuleMapping;


public interface CategoryModuleMappingRepository extends JpaRepository<CategoryModuleMapping, Long> ,QueryDslPredicateExecutor<CategoryModuleMapping> {
	
	public CategoryModuleMapping  findByCategoryIdAndModuleId(Long categoryId,Long moduleId);

}