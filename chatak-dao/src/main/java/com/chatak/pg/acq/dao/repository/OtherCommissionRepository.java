/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGOtherCommission;


/**
 * @Author: Girmiti Software
 * @Date: Aug 31, 2016
 * @Time: 11:27:19 AM
 * @Version: 1.0
 * @Comments: 
 *
 */

public interface OtherCommissionRepository extends JpaRepository<PGOtherCommission, Long>, QueryDslPredicateExecutor<PGOtherCommission> {

	List<PGOtherCommission> findByCommissionProgramId(Long commissionProgramId);
}
