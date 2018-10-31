/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Author: Girmiti Software
 * @Date: May 3, 2018
 * @Time: 2:22:25 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_NULL)
public class CardProgramResponse  extends Response{
	/**
	 * 
	 */
	private static final long serialVersionUID = 175805499686423993L;
	
	private List<CardProgramRequest> cardProgramList;
	
	private List<CardProgramRequest> issuanceCardProgramList;

	/**
	 * @return the cardProgramList
	 */
	public List<CardProgramRequest> getCardProgramList() {
		return cardProgramList;
	}

	/**
	 * @param cardProgramList the cardProgramList to set
	 */
	public void setCardProgramList(List<CardProgramRequest> cardProgramList) {
		this.cardProgramList = cardProgramList;
	}

	/**
	 * @return the issuanceCardProgramList
	 */
	public List<CardProgramRequest> getIssuanceCardProgramList() {
		return issuanceCardProgramList;
	}

	/**
	 * @param issuanceCardProgramList the issuanceCardProgramList to set
	 */
	public void setIssuanceCardProgramList(List<CardProgramRequest> issuanceCardProgramList) {
		this.issuanceCardProgramList = issuanceCardProgramList;
	}

}