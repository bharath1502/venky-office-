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
	
	public List<CardProgramRequest> findByBankId(Long bankId);
	
	public CardProgram findByIssuanceCardProgramId(Long issuanceCardProgramId);
	
	public CardProgram findCardProgramByIIN(Long iin, String partnerIINCode, String iinExt);
	public List<CardProgramRequest> getCardProgramListForFeeProgram();
}
