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
 * @Date: May 9, 2018
 * @Time: 2:18:00 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_ISO_ACCOUNT")
public class IsoAccount implements Serializable{

	private static final long serialVersionUID = 2179099868367224282L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;
	  
	  @Column(name = "ACCOUNT_NUMBER",updatable=false)
	  private Long accountNumber;
	  
	  @Column(name = "ISO_ID")
	  private Long isoId;
	  
	  @Column(name = "CURRENT_BALANCE")
	  private Long currentBalance;
	  
	  @Column(name = "AVAILABLE_BALANCE")
	  private Long availableBalance;
	  
	  @Column(name = "STATUS")
	  private String status;
	  
	  @Column(name = "CREATED_DATE", updatable = false)
	  private Timestamp createdDate;
	  
	  @Column(name = "UPDATED_DATE")
	  private Timestamp updatedDate;
	  
	  @Column(name = "CREATED_BY", updatable = false)
	  private String createdBy;
	  
	  @Column(name = "UPDATED_BY")
	  private String updatedBy;
	  
	  @Column(name = "ACCOUNT_TYPE")
	  private String accountType;

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
	 * @return the accountNumber
	 */
	public Long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the isoId
	 */
	public Long getIsoId() {
		return isoId;
	}

	/**
	 * @param isoId the isoId to set
	 */
	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	/**
	 * @return the currentBalance
	 */
	public Long getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(Long currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the availableBalance
	 */
	public Long getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
