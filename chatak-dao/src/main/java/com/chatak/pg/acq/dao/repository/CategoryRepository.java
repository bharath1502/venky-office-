package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import com.chatak.pg.acq.dao.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, QueryDslPredicateExecutor<Category> {

	Category findByCategoryId(Long categoryId);

}
