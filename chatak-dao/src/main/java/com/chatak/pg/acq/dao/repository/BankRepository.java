/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.chatak.pg.acq.dao.model.PGBank;

/**
 * @Author: Girmiti Software
 * @Date: Aug 2, 2016
 * @Time: 4:15:23 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface BankRepository extends JpaRepository<PGBank, Long>, QueryDslPredicateExecutor<PGBank> {
	
	@Query("select t from PGBank t where t.bankName=:bankName and t.status <> 'Deleted' ")
	public PGBank findByBankName(@Param("bankName") String bankName);
	
	public PGBank findByBankNameAndStatusNotLike(String bankName, String status);
	
	public PGBank findById(Long bankId);
	
	public List<PGBank> findByCurrencyIdAndStatusLike(Long currencyId, String status);
	

}
