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
 * @Date: May 7, 2018
 * @Time: 10:49:58 AM
 * @Version: 1.0
 * @Comments:
 *
 */
@Entity
@Table(name = "PG_CARD_PROGRAM")
public class CardProgram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1800326496124279360L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long cardProgramId;

	@Column(name = "ISSUANCE_CARD_PROGRAM_ID")
	private Long issuanceCradProgramId;
	
	@Column(name = "ACQ_PM_ID")
    private Long acquirerPmId;

	@Column(name = "CARD_PROGRAM_NAME")
	private String cardProgramName;

	@Column(name = "ISSUANCE_PARTNER_ID")
	private Long partnerId;

	@Column(name = "ISSUANCE_PARTNER_NAME")
	private String partnerName;

	@Column(name = "IIN")
	private String iin;

	@Column(name = "IIN_EXT")
	private String iinExt;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_DATE", updatable = false)
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;
	
	@Column(name = "IIN_PARTNER_EXT")
	private String  partnerIINCode;

	public Long getCardProgramId() {
		return cardProgramId;
	}

	public String getCardProgramName() {
		return cardProgramName;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getIin() {
		return iin;
	}

	public void setIin(String iin) {
		this.iin = iin;
	}

	public String getIinExt() {
		return iinExt;
	}

	public void setIinExt(String iinExt) {
		this.iinExt = iinExt;
	}

	public String getStatus() {
		return status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCardProgramId(Long cardProgramId) {
		this.cardProgramId = cardProgramId;
	}

	public void setCardProgramName(String cardProgramName) {
		this.cardProgramName = cardProgramName;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the issuanceCradProgramId
	 */
	public Long getIssuanceCradProgramId() {
		return issuanceCradProgramId;
	}

	/**
	 * @param issuanceCradProgramId
	 *            the issuanceCradProgramId to set
	 */
	public void setIssuanceCradProgramId(Long issuanceCradProgramId) {
		this.issuanceCradProgramId = issuanceCradProgramId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	/**
	 * @return the partnerIINCode
	 */
	public String getPartnerIINCode() {
		return partnerIINCode;
	}

	/**
	 * @param partnerIINCode the partnerIINCode to set
	 */
	public void setPartnerIINCode(String partnerIINCode) {
		this.partnerIINCode = partnerIINCode;
	}

  /**
   * @return the acquirerPmId
   */
  public Long getAcquirerPmId() {
    return acquirerPmId;
  }

  /**
   * @param acquirerPmId the acquirerPmId to set
   */
  public void setAcquirerPmId(Long acquirerPmId) {
    this.acquirerPmId = acquirerPmId;
  }

}
