/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 5, 2016
 * @Time: 2:36:14 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_BLACKLISTED_CARD")
public class PGBlackListedCard {
	
	  @Id
	  /*@SequenceGenerator(name = "SEQ_PG_BLACKLISTED_CARD", sequenceName = "SEQ_PG_BLACKLISTED_CARD")
	  @GeneratedValue(generator = "SEQ_PG_BLACKLISTED_CARD")*/
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;

	  @Column(name = "CARD_NUM")
	  private Long cardNumber;

	  @Column(name = "CREATED_DATE")
	  private Timestamp createdDate;

	  @Column(name = "STATUS")
	  private Integer status;
	  
	  @Column(name = "REASON")
	  private String reason;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cardNumber
	 */
	public Long getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	  
}
