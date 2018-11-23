/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGCommission;

/**
 * @Author: Girmiti Software
 * @Date: Aug 25, 2016
 * @Time: 11:43:30 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CommissionProgramRepository extends JpaRepository<PGCommission, Long>, QueryDslPredicateExecutor<PGCommission>
{

	/**
	 * @param commissionProgramId
	 * @return
	 */
	List<PGCommission> findByCommissionProgramId(Long commissionProgramId);

	/**
	 * @param commissionName
	 * @return
	 */
	List<PGCommission> findByCommissionName(String commissionName);

}
