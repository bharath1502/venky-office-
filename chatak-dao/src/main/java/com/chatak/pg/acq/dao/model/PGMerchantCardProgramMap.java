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
 * @Date: May 9, 2018
 * @Time: 12:34:39 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_MERCHANT_CARD_PROGRAM_MAPPING")
public class PGMerchantCardProgramMap implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MERCHANT_ID ")
	private Long merchantId;

	@Column(name = "CARD_PROGRAM_ID")
	private Long cardProgramId;
	
	@Column(name = "ENTITY_TYPE")
	private String entitytype;

	@Column(name = "ENTITY_ID")
	private Long entityId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getCardProgramId() {
		return cardProgramId;
	}

	public void setCardProgramId(Long cardProgramId) {
		this.cardProgramId = cardProgramId;
	}

	/**
	 * @return the entitytype
	 */
	public String getEntitytype() {
		return entitytype;
	}

	/**
	 * @param entitytype the entitytype to set
	 */
	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

}