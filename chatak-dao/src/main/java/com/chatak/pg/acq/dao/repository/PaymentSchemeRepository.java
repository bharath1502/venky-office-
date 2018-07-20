/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.PGPaymentScheme;

/**
 * @Author: Girmiti Software
 * @Date: Aug 6, 2016
 * @Time: 10:50:06 AM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface PaymentSchemeRepository extends JpaRepository<PGPaymentScheme, Long>, QueryDslPredicateExecutor<PGPaymentScheme> 
{
	public PGPaymentScheme findById(Long id);
	
	public List<PGPaymentScheme> findByContactEmailOrderByUpdatedDateDesc(String contactEmail);
	
	public PGPaymentScheme findByContactEmail(String contactEmail);
	
	public PGPaymentScheme findByPaymentSchemeName(String paymentSchemeName);
}
 