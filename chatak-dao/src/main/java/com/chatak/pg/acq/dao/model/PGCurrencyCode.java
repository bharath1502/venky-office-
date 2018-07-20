package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_CURRENCY_CODE")
public class PGCurrencyCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "COUNTRY_ID")
	private Long countryId;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CURRENCY_NAME")
	private String currencyName;
	
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "CURRENCY_CODE_NUMERIC")
	private String currencyCodeNumeric;
	
	@Column(name = "CURRENCY_CODE_ALPHA")
	private String currencyCodeAlpha;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the countryId
	 */
	public Long getCountryId() {
		return countryId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @return the currencyCodeNumeric
	 */
	public String getCurrencyCodeNumeric() {
		return currencyCodeNumeric;
	}
	
	/**
	 * @return the currencyCodeAlpha
	 */
	public String getCurrencyCodeAlpha() {
		return currencyCodeAlpha;
	}

	/**
	 * @param currencyCodeNumeric the currencyCodeNumeric to set
	 */
	public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
		this.currencyCodeNumeric = currencyCodeNumeric;
	}

	/**
	 * @param currencyCodeAlpha the currencyCodeAlpha to set
	 */
	public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
		this.currencyCodeAlpha = currencyCodeAlpha;
	}
	
}
