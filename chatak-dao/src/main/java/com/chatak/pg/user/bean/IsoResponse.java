/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.bean.Response;

/**
 * @Author: Girmiti Software
 * @Date: May 8, 2018
 * @Time: 6:22:58 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class IsoResponse extends Response{

	private static final long serialVersionUID = -7099418964280882168L;
	private List<IsoRequest> isoRequest;
	private List<ProgramManagerRequest> programManagerRequestList;
	private List<CardProgramRequest> cardProgramRequestList;
	
	/**
	 * @return the programManagerRequestList
	 */
	public List<ProgramManagerRequest> getProgramManagerRequestList() {
		return programManagerRequestList;
	}
	/**
	 * @param programManagerRequestList the programManagerRequestList to set
	 */
	public void setProgramManagerRequestList(
			List<ProgramManagerRequest> programManagerRequestList) {
		this.programManagerRequestList = programManagerRequestList;
	}
	/**
	 * @return the cardProgramRequestList
	 */
	public List<CardProgramRequest> getCardProgramRequestList() {
		return cardProgramRequestList;
	}
	/**
	 * @param cardProgramRequestList the cardProgramRequestList to set
	 */
	public void setCardProgramRequestList(
			List<CardProgramRequest> cardProgramRequestList) {
		this.cardProgramRequestList = cardProgramRequestList;
	}
	public List<IsoRequest> getIsoRequest() {
		return isoRequest;
	}
	public void setIsoRequest(List<IsoRequest> isoRequest) {
		this.isoRequest = isoRequest;
	}
}
