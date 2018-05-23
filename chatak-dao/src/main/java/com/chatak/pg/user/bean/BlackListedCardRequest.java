/**
 * 
 */
package com.chatak.pg.user.bean;

import java.sql.Timestamp;

/**
 * @Author: Girmiti Software
 * @Date: Aug 1, 2016
 * @Time: 11:19:44 AM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class BlackListedCardRequest extends SearchRequest {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long cardNumber;
	
	private Integer status;
	
	private String statusDisp;
	
	private String createdBy;
	
	private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
	private String updatedBy;
	
	private String reason;

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
	 * @return the statusDisp
	 */
	public String getStatusDisp() {
		return statusDisp;
	}

	/**
	 * @param statusDisp the statusDisp to set
	 */
	public void setStatusDisp(String statusDisp) {
		this.statusDisp = statusDisp;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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