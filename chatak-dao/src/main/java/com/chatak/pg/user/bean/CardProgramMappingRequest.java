/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;

/**
 * @Author: Girmiti Software
 * @Date: May 7, 2018
 * @Time: 11:53:16 AM
 * @Version: 1.0
 * @Comments:
 *
 */
public class CardProgramMappingRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long cardProgramId;

	private Long programManagerId;
	
	private String cardProgramName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cardProgramId
	 */
	public Long getCardProgramId() {
		return cardProgramId;
	}

	/**
	 * @param cardProgramId
	 *            the cardProgramId to set
	 */
	public void setCardProgramId(Long cardProgramId) {
		this.cardProgramId = cardProgramId;
	}

	/**
	 * @return the programManagerId
	 */
	public Long getProgramManagerId() {
		return programManagerId;
	}

	/**
	 * @param programManagerId
	 *            the programManagerId to set
	 */
	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	/**
	 * @return the cardProgramName
	 */
	public String getCardProgramName() {
		return cardProgramName;
	}

	/**
	 * @param cardProgramName the cardProgramName to set
	 */
	public void setCardProgramName(String cardProgramName) {
		this.cardProgramName = cardProgramName;
	}
}
