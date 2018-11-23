package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_CURRENCY_CONFIG")
public class PGCurrencyConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	/*@SequenceGenerator(name = "SEQ_PG_CURRENCY_CONFIG_ID", sequenceName = "SEQ_PG_CURRENCY_CONFIG")
	@GeneratedValue(generator = "SEQ_PG_CURRENCY_CONFIG_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CURRENCY_NAME")
	private String currencyName;
	
	@Column(name = "CURRENCY_CODE_NUMERIC")
	private String currencyCodeNumeric;
	
	@Column(name = "CURRENCY_CODE_ALPHA")
	private String currencyCodeAlpha;
	
	@Column(name = "CURRENCY_EXPONENT")
	private Integer currencyExponent;
	
	@Column(name = "CURRENCY_SEPARATOR_POSITION")
	private Integer currencySeparatorPosition;
	
	@Column(name = "CURRENCY_MINOR_SEPARATOR_UNIT")
	private Character currencyMinorUnit;
	
	@Column(name = "CURRENCY_THOUS_SEPARATOR_UNIT")
	private Character currencyThousandsUnit;
	
 	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	
	@Column(name = "MODIFIED_DATE")
	private Timestamp modifiedDate;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "REASON")
	private String reason;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param currencyName the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param currencyCodeNumeric the currencyCodeNumeric to set
	 */
	public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
		this.currencyCodeNumeric = currencyCodeNumeric;
	}

	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @param currencyCodeAlpha the currencyCodeAlpha to set
	 */
	public void setCurrencyCodeAlpha(String currencyCodeAlpha) {
		this.currencyCodeAlpha = currencyCodeAlpha;
	}

	/**
	 * @return the currencyExponent
	 */
	public Integer getCurrencyExponent() {
		return currencyExponent;
	}
	
	/**
	 * @return the currencyCodeAlpha
	 */
	public String getCurrencyCodeAlpha() {
		return currencyCodeAlpha;
	}

	/**
	 * @param currencyExponent the currencyExponent to set
	 */
	public void setCurrencyExponent(Integer currencyExponent) {
		this.currencyExponent = currencyExponent;
	}
	
	/**
	 * @return the currencyCodeNumeric
	 */
	public String getCurrencyCodeNumeric() {
		return currencyCodeNumeric;
	}

	/**
	 * @return the currencySeparatorPosition
	 */
	public Integer getCurrencySeparatorPosition() {
		return currencySeparatorPosition;
	}

	/**
	 * @return the currencyMinorUnit
	 */
	public Character getCurrencyMinorUnit() {
		return currencyMinorUnit;
	}
	
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param currencyMinorUnit the currencyMinorUnit to set
	 */
	public void setCurrencyMinorUnit(Character currencyMinorUnit) {
		this.currencyMinorUnit = currencyMinorUnit;
	}

	/**
	 * @param currencySeparatorPosition the currencySeparatorPosition to set
	 */
	public void setCurrencySeparatorPosition(Integer currencySeparatorPosition) {
		this.currencySeparatorPosition = currencySeparatorPosition;
	}

	/**
	 * @param currencyThousandsUnit the currencyThousandsUnit to set
	 */
	public void setCurrencyThousandsUnit(Character currencyThousandsUnit) {
		this.currencyThousandsUnit = currencyThousandsUnit;
	}
	
	/**
	 * @return the currencyThousandsUnit
	 */
	public Character getCurrencyThousandsUnit() {
		return currencyThousandsUnit;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @return the modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
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
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
