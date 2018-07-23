/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: May 7, 2018
 * @Time: 11:41:52 AM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_PM_CARD_PROGRAM_MAPPING")
public class PmCardProgamMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CARD_PROGRAM_ID")
	private Long cardProgramId;

	@Column(name = "PM_ID")
	private Long programManagerId;

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
}
