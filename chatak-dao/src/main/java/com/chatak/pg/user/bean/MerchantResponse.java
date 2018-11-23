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
 * @Date: May 10, 2018
 * @Time: 5:43:13 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class MerchantResponse {

	private com.chatak.pg.model.Merchant merchant;
	
	private List<ProgramManagerRequest> programManagerRequests;
	
	private List<IsoRequest> isoRequests;
	
	private List<CardProgramRequest> cardProgramRequests;

	public com.chatak.pg.model.Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(com.chatak.pg.model.Merchant merchant) {
		this.merchant = merchant;
	}

	public List<ProgramManagerRequest> getProgramManagerRequests() {
		return programManagerRequests;
	}

	public void setProgramManagerRequests(List<ProgramManagerRequest> programManagerRequests) {
		this.programManagerRequests = programManagerRequests;
	}

	public List<IsoRequest> getIsoRequests() {
		return isoRequests;
	}

	public void setIsoRequests(List<IsoRequest> isoRequests) {
		this.isoRequests = isoRequests;
	}

	public List<CardProgramRequest> getCardProgramRequests() {
		return cardProgramRequests;
	}

	public void setCardProgramRequests(List<CardProgramRequest> cardProgramRequests) {
		this.cardProgramRequests = cardProgramRequests;
	}
}
