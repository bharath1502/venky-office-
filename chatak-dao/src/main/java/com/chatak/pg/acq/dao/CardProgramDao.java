/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PmCardProgamMapping;
import com.chatak.pg.acq.dao.model.CardProgram;
import com.chatak.pg.user.bean.CardProgramRequest;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 8:30:43 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CardProgramDao {

    public CardProgram createCardProgram(CardProgram cardProgramRequest);
	
	public PmCardProgamMapping createCardProgramMapping(PmCardProgamMapping cardProgramRequest);
	
	public CardProgram findByCardProgramId(Long cardProgramId);
	
	public List<CardProgramRequest> findCardProgramByPmId(Long programManagerId);
	
	public CardProgram findByIssuanceCardProgramId(Long issuanceCardProgramId);
	
	public Long findCardProgramByIIN(String iin, String partnerIINCode, String iinExt);
	public List<CardProgramRequest> getCardProgramListForFeeProgram();
	
	public CardProgram findCardProgramIdByIinAndPartnerIINCodeAndIinExt(String iin, String partnerIINCode, String iinExt);

	public List<CardProgram> findByCurrency(String currency) ;
	
	public List<CardProgramRequest> getUnselectedCpByPm(Long programManagerId);
	
	public List<CardProgramRequest> getUnselectedCpForIndependentPm(Long programManagerId, String currency);
}
