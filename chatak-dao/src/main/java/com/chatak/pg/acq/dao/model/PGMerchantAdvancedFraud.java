/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 11, 2016
 * @Time: 2:29:21 PM
 * @Version: 1.0
 * @Comments:
 *
 */

@Entity
@Table(name = "PG_MERCHANT_ADVANCED_FRAUD")
public class PGMerchantAdvancedFraud implements Serializable {

	private static final long serialVersionUID = -1653096596185219662L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_MERCHANT_ADVANCED_FRAUD_ID", sequenceName = "SEQ_PG_MERCHANT_ADVANCED_FRAUD")
	@GeneratedValue(generator = "SEQ_PG_MERCHANT_ADVANCED_FRAUD_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FILTER_TYPE")
	private String filterType;

	@Column(name = "FILTER_ON")
	private String filterOn;

	@Column(name = "DURATION")
	private String duration;

	@Column(name = "TRANSACTION_LIMIT")
	private String transactionLimit;

	@Column(name = "MAX_LIMIT")
	private String maxLimit;
	
	@Column(name = "ACTION")
	private String action;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "MERCHANT_CODE")
	private String merchantCode;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
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
	 * @return the filterType
	 */
	public String getFilterType() {
		return filterType;
	}

	/**
	 * @param filterType
	 *            the filterType to set
	 */
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	/**
	 * @return the filterOn
	 */
	public String getFilterOn() {
		return filterOn;
	}

	/**
	 * @param filterOn
	 *            the filterOn to set
	 */
	public void setFilterOn(String filterOn) {
		this.filterOn = filterOn;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the transactionLimit
	 */
	public String getTransactionLimit() {
		return transactionLimit;
	}

	/**
	 * @param transactionLimit
	 *            the transactionLimit to set
	 */
	public void setTransactionLimit(String transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	/**
	 * @return the maxLimit
	 */
	public String getMaxLimit() {
		return maxLimit;
	}

	/**
	 * @param maxLimit
	 *            the maxLimit to set
	 */
	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
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
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param merchantCode
	 *            the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

}