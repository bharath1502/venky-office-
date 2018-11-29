/**
 * 
 */
package com.chatak.pg.acq.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

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
	public CardProgram findByIinAndPartnerIINCodeAndIinExt(String iin,String partnerIINCode,String iinExt);
	public List<CardProgram> findByCurrency(String currency);
	
	@Query("select cp.cardProgramId from CardProgram cp where cp.iin = :iin and cp.partnerIINCode = :partnerIINCode and cp.iinExt = :iinExt")
	public Long findCardProgramIdByIinAndPartnerIINCodeAndIinExt(@Param("iin")String iin, 
			@Param("partnerIINCode")String partnerIINCode, @Param("iinExt")String iinExt);
}
