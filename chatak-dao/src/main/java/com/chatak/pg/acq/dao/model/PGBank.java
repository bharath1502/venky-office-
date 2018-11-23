/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: Aug 2, 2016
 * @Time: 3:54:30 PM
 * @Version: 1.0
 * @Comments:
 *
 */

@Entity
@Table(name = "PG_BANK")
public class PGBank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1964828793299447235L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "BANK_ID")	
	private List<PGBankCurrencyMapping> pgBankCurrencyMapping;

	@Column(name = "ISSUANCE_BANK_ID")
	private Long issuanceBankId;
	
	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "BANK_SHORT_NAME")
	private String bankShortName;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "ACQUIRER_ID")
	private String acqirerId;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "ACCOUNT_NUM")
	private Long revenueAccountNumber;
	
	@Column(name="CURRENCY_ID")
	private Long currencyId;
	
	@Column(name="REASON")
	private String reason;
	
	@Column(name = "SETTL_ROUTING_NUMBER")
	private String settlRoutingNumber;
	
	@Column(name = "BANK_CODE")
	private String bankCode;
	
	@Column(name="EXTENSION")
	private String extension;
	
	@Column(name = "SETTL_ACCOUNT_NUMBER")
	private String settlAccountNumber;
	
	@Column(name = "CONTACT_PERSON_PHONE")
	private String contactPersonPhone;
	
	@Column(name = "CONTACT_PERSON_CELL")
	private String contactPersonCell;
	
	@Column(name = "CONTACT_PERSON_NAME")
	private String contactPersonName;
	
	@Column(name = "CONTACT_PERSON_FAX")
	private String contactPersonFax;
	
	@Column(name = "CONTACT_PERSON_EMAIL")
	private String contactPersonEmail;

	public Long getCurrencyId() {
		return currencyId;
	}

	
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getRevenueAccountNumber() {
		return revenueAccountNumber;
	}

	public void setRevenueAccountNumber(Long revenueAccountNumber) {
		this.revenueAccountNumber = revenueAccountNumber;
	}

	public List<PGBankCurrencyMapping> getPgBankCurrencyMapping() {
		return pgBankCurrencyMapping;
	}

	public void setPgBankCurrencyMapping(
			List<PGBankCurrencyMapping> pgBankCurrencyMapping) {
		this.pgBankCurrencyMapping = pgBankCurrencyMapping;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcqirerId() {
		return acqirerId;
	}

	public void setAcqirerId(String acqirerId) {
		this.acqirerId = acqirerId;
	}

	public String getAddress1() {
		return address1;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAddress2() {
		return address2;
	}
	
	public String getBankShortName() {
		return bankShortName;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStatus() {
		return status;
	}

	public String getState() {
		return state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getReason() {
		return reason;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	/**
	 * @return the issuanceBankId
	 */
	public Long getIssuanceBankId() {
		return issuanceBankId;
	}


	/**
	 * @param issuanceBankId the issuanceBankId to set
	 */
	public void setIssuanceBankId(Long issuanceBankId) {
		this.issuanceBankId = issuanceBankId;
	}


	/**
	 * @return the settlRoutingNumber
	 */
	public String getSettlRoutingNumber() {
		return settlRoutingNumber;
	}


	/**
	 * @param settlRoutingNumber the settlRoutingNumber to set
	 */
	public void setSettlRoutingNumber(String settlRoutingNumber) {
		this.settlRoutingNumber = settlRoutingNumber;
	}


	/**
	 * @return the settlAccountNumber
	 */
	public String getSettlAccountNumber() {
		return settlAccountNumber;
	}


	public String getContactPersonPhone() {
		return contactPersonPhone;
	}


	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}


	public String getContactPersonCell() {
		return contactPersonCell;
	}


	public void setContactPersonCell(String contactPersonCell) {
		this.contactPersonCell = contactPersonCell;
	}


	public String getContactPersonName() {
		return contactPersonName;
	}


	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}


	public String getContactPersonFax() {
		return contactPersonFax;
	}


	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}


	public String getContactPersonEmail() {
		return contactPersonEmail;
	}


	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	/**
	 * @param settlAccountNumber the settlAccountNumber to set
	 */
	public void setSettlAccountNumber(String settlAccountNumber) {
		this.settlAccountNumber = settlAccountNumber;
	}
	
}
