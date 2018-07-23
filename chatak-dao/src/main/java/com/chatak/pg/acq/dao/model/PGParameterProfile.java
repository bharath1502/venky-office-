package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pg_parameter_profile")
public class PGParameterProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7181515518602893465L;

	@Id
	/*@SequenceGenerator(name = "SEQ_PG_PARAMETER_PROFILE_PROFILE_ID", sequenceName = "SEQ_PG_PARAMETER_PROFILE")
	@GeneratedValue(generator = "SEQ_PG_PARAMETER_PROFILE_PROFILE_ID")*/
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "profile_id")
	private Long profileId;

	@Column(name = "profile_name")
	private String profileName;
	
	@Column(name = "terminal_type")
	private String terminalType;
	
	@Column(name = "attended")
	private String attended;
	
	@Column(name = "unAttended")
	private String unAttended;
	
	@Column(name = "iso_country_code")
	private String isoCountryCode;
	
	@Column(name = "currency_code")
	private String currencyCode;
	
	@Column(name = "local_currency_code")
	private String localCurrencyCode;
	
	@Column(name = "default_cdol")
	private String defaultCdol;
	
	@Column(name = "default_ddol")
	private String defaultDdol;
	
	@Column(name = "default_tdol")
	private String defaultTdol;
	
	@Column(name = "terminal_floor_limit")
	private String terminalFloorLimit;
	
	@Column(name = "target_percentage")
	private String targetPercentage;
	
	@Column(name = "maxt_target_percentage")
	private String maxTargetPercentage;
	
	@Column(name = "status")
	private String status;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "terminal_capabilities_id")
	PGTerminalCapabilities terminalCapabilities;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "attend_financial_institute")
	private Boolean attendFinancialInstitute;

	@Column(name = "attend_merchant")
	private Boolean attendMerchant;
	
	
	@Column(name = "unAttend_financial_institute")
	private Boolean unAttendfinancialInstitute;

	@Column(name = "unAttend_merchant")
	private Boolean unAttendMerchant;
	
	@Column(name = "parameter_type")
	private String parameterType;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "unAttend_cardHolder")
	private Boolean unAttendCardHolder;
	

	/**
	 * @return the unAttendCardHolder
	 */
	public Boolean getUnAttendCardHolder() {
		return unAttendCardHolder;
	}

	/**
	 * @param unAttendCardHolder the unAttendCardHolder to set
	 */
	public void setUnAttendCardHolder(Boolean unAttendCardHolder) {
		this.unAttendCardHolder = unAttendCardHolder;
	}

	/**
	 * @return the profileId
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the terminalType
	 */
	public String getTerminalType() {
		return terminalType;
	}

	/**
	 * @param terminalType the terminalType to set
	 */
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	/**
	 * @return the attended
	 */
	public String getAttended() {
		return attended;
	}

	/**
	 * @param attended the attended to set
	 */
	public void setAttended(String attended) {
		this.attended = attended;
	}

	/**
	 * @return the isoCountryCode
	 */
	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	/**
	 * @param isoCountryCode the isoCountryCode to set
	 */
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the localCurrencyCode
	 */
	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}

	/**
	 * @param localCurrencyCode the localCurrencyCode to set
	 */
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}

	/**
	 * @return the defaultCdol
	 */
	public String getDefaultCdol() {
		return defaultCdol;
	}

	/**
	 * @param defaultCdol the defaultCdol to set
	 */
	public void setDefaultCdol(String defaultCdol) {
		this.defaultCdol = defaultCdol;
	}

	/**
	 * @return the defaultDdol
	 */
	public String getDefaultDdol() {
		return defaultDdol;
	}

	/**
	 * @param defaultDdol the defaultDdol to set
	 */
	public void setDefaultDdol(String defaultDdol) {
		this.defaultDdol = defaultDdol;
	}

	/**
	 * @return the defaultTdol
	 */
	public String getDefaultTdol() {
		return defaultTdol;
	}

	/**
	 * @param defaultTdol the defaultTdol to set
	 */
	public void setDefaultTdol(String defaultTdol) {
		this.defaultTdol = defaultTdol;
	}

	/**
	 * @return the terminalFloorLimit
	 */
	public String getTerminalFloorLimit() {
		return terminalFloorLimit;
	}

	/**
	 * @param terminalFloorLimit the terminalFloorLimit to set
	 */
	public void setTerminalFloorLimit(String terminalFloorLimit) {
		this.terminalFloorLimit = terminalFloorLimit;
	}

	/**
	 * @return the targetPercentage
	 */
	public String getTargetPercentage() {
		return targetPercentage;
	}

	/**
	 * @param targetPercentage the targetPercentage to set
	 */
	public void setTargetPercentage(String targetPercentage) {
		this.targetPercentage = targetPercentage;
	}

	/**
	 * @return the maxTargetPercentage
	 */
	public String getMaxTargetPercentage() {
		return maxTargetPercentage;
	}

	/**
	 * @param maxTargetPercentage the maxTargetPercentage to set
	 */
	public void setMaxTargetPercentage(String maxTargetPercentage) {
		this.maxTargetPercentage = maxTargetPercentage;
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
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @return the terminalCapabilities
	 */
	public PGTerminalCapabilities getTerminalCapabilities() {
		return terminalCapabilities;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param terminalCapabilities the terminalCapabilities to set
	 */
	public void setTerminalCapabilities(PGTerminalCapabilities terminalCapabilities) {
		this.terminalCapabilities = terminalCapabilities;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the unAttended
	 */
	public String getUnAttended() {
		return unAttended;
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
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	/**
	 * @param unAttended the unAttended to set
	 */
	public void setUnAttended(String unAttended) {
		this.unAttended = unAttended;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the attendFinancialInstitute
	 */
	public Boolean getAttendFinancialInstitute() {
		return attendFinancialInstitute;
	}

	/**
	 * @param attendFinancialInstitute the attendFinancialInstitute to set
	 */
	public void setAttendFinancialInstitute(Boolean attendFinancialInstitute) {
		this.attendFinancialInstitute = attendFinancialInstitute;
	}

	/**
	 * @return the attendMerchant
	 */
	public Boolean getAttendMerchant() {
		return attendMerchant;
	}

	/**
	 * @param attendMerchant the attendMerchant to set
	 */
	public void setAttendMerchant(Boolean attendMerchant) {
		this.attendMerchant = attendMerchant;
	}

	/**
	 * @return the unAttendfinancialInstitute
	 */
	public Boolean getUnAttendfinancialInstitute() {
		return unAttendfinancialInstitute;
	}

	/**
	 * @param unAttendfinancialInstitute the unAttendfinancialInstitute to set
	 */
	public void setUnAttendfinancialInstitute(Boolean unAttendfinancialInstitute) {
		this.unAttendfinancialInstitute = unAttendfinancialInstitute;
	}

	/**
	 * @return the unAttendMerchant
	 */
	public Boolean getUnAttendMerchant() {
		return unAttendMerchant;
	}

	/**
	 * @param unAttendMerchant the unAttendMerchant to set
	 */
	public void setUnAttendMerchant(Boolean unAttendMerchant) {
		this.unAttendMerchant = unAttendMerchant;
	}

	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
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
