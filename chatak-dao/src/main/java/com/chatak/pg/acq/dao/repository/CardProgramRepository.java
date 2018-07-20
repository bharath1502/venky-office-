/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.chatak.pg.acq.dao.model.CardProgram;

/**
 * @Author: Girmiti Software
 * @Date: May 7, 2018
 * @Time: 3:39:58 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CardProgramRepository extends JpaRepository<CardProgram, Long>,QueryDslPredicateExecutor<CardProgram> {

	public CardProgram findByCardProgramId(Long cardProgramId);
	public CardProgram findByIssuanceCradProgramId(Long issuanceCardProgramId);	
	public CardProgram findByIinAndPartnerIINCodeAndIinExt(Long iin,String partnerIINCode,String iinExt);
}
