package com.chatak.pg.model;

import java.io.Serializable;

import com.chatak.pg.bean.SearchRequest;

public class ParameterProfileDTO extends SearchRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7181515518602893465L;

	private Long profileId;
 
	private String profileName;
	
	private String terminalProfileName;
	
	private String terminalType;
	
	private String attended;
	
	private String unAttended;
	
	private String operatedBy;
	
    private Boolean attendFinancialInstitute;
	
	private Boolean attendMerchant;
	
	 private Boolean unAttendfinancialInstitute;
		
    private Boolean unAttendMerchant;
	
	private String defaultCdol;
	
	private String defaultDdol;
	
	private String defaultTdol;
	
	private String terminalFloorLimit;
	
	private String targetPercentage;
	
	private String maxTargetPercentage;
	
	private String status;
	
	TerminalCapabilitiesDTO terminalCapabilities;
	
	private String isoCountryCode;
	
	private String localCurrencyCode;
	
	private String currencyCode;
	
	private String parameterType;
	
	private Long oldProfileId;
	
	private Boolean unAttendCardHolder;
	 

	/**
   * @param attended the attended to set
   */
  public void setAttended(String attended) {
    this.attended = attended;
  }
  
	/**
	 * @return the unAttendCardHolder
	 */
	public Boolean getUnAttendCardHolder() {
		return unAttendCardHolder;
	}
	
	/**
   * @return the profileId
   */
  public Long getProfileId() {
    return profileId;
  }

	/**
	 * @param unAttendCardHolder the unAttendCardHolder to set
	 */
	public void setUnAttendCardHolder(Boolean unAttendCardHolder) {
		this.unAttendCardHolder = unAttendCardHolder;
	}

	/**
   * @return the profileName
   */
  public String getProfileName() {
    return profileName;
  }
  
  /**
   * @return the terminalType
   */
  public String getTerminalType() {
    return terminalType;
  }

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/**
   * @return the attended
   */
  public String getAttended() {
    return attended;
  }

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @param terminalType the terminalType to set
	 */
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	/**
	 * @return the operatedBy
	 */
	public String getOperatedBy() {
		return operatedBy;
	}

	/**
	 * @param operatedBy the operatedBy to set
	 */
	public void setOperatedBy(String operatedBy) {
		this.operatedBy = operatedBy;
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
   * @param defaultDdol the defaultDdol to set
   */
  public void setDefaultDdol(String defaultDdol) {
    this.defaultDdol = defaultDdol;
  }

	/**
	 * @return the defaultCdol
	 */
	public String getDefaultCdol() {
		return defaultCdol;
	}
	
	/**
   * @param defaultTdol the defaultTdol to set
   */
  public void setDefaultTdol(String defaultTdol) {
    this.defaultTdol = defaultTdol;
  }

	/**
	 * @param defaultCdol the defaultCdol to set
	 */
	public void setDefaultCdol(String defaultCdol) {
		this.defaultCdol = defaultCdol;
	}
	
	/**
   * @return the defaultTdol
   */
  public String getDefaultTdol() {
    return defaultTdol;
  }

	/**
	 * @return the defaultDdol
	 */
	public String getDefaultDdol() {
		return defaultDdol;
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
	 * @return the unAttended
	 */
	public String getUnAttended() {
		return unAttended;
	}
	
	/**
   * @return the attendMerchant
   */
  public Boolean getAttendMerchant() {
    return attendMerchant;
  }

	/**
	 * @param unAttended the unAttended to set
	 */
	public void setUnAttended(String unAttended) {
		this.unAttended = unAttended;
	}
	
	/**
   * @param attendMerchant the attendMerchant to set
   */
  public void setAttendMerchant(Boolean attendMerchant) {
    this.attendMerchant = attendMerchant;
  }

	
	/**
	 * @return the attendFinancialInstitute
	 */
	public Boolean getAttendFinancialInstitute() {
		return attendFinancialInstitute;
	}
	
	/**
   * @return the unAttendfinancialInstitute
   */
  public Boolean getUnAttendfinancialInstitute() {
    return unAttendfinancialInstitute;
  }

	/**
	 * @param attendFinancialInstitute the attendFinancialInstitute to set
	 */
	public void setAttendFinancialInstitute(Boolean attendFinancialInstitute) {
		this.attendFinancialInstitute = attendFinancialInstitute;
	}

	/**
   * @return the unAttendMerchant
   */
  public Boolean getUnAttendMerchant() {
    return unAttendMerchant;
  }

	/**
	 * @param unAttendfinancialInstitute the unAttendfinancialInstitute to set
	 */
	public void setUnAttendfinancialInstitute(Boolean unAttendfinancialInstitute) {
		this.unAttendfinancialInstitute = unAttendfinancialInstitute;
	}

	/**
	 * @param unAttendMerchant the unAttendMerchant to set
	 */
	public void setUnAttendMerchant(Boolean unAttendMerchant) {
		this.unAttendMerchant = unAttendMerchant;
	}

	/**
	 * @return the terminalCapabilities
	 */
	public TerminalCapabilitiesDTO getTerminalCapabilities() {
		return terminalCapabilities;
	}

	/**
	 * @param terminalCapabilities the terminalCapabilities to set
	 */
	public void setTerminalCapabilities(TerminalCapabilitiesDTO terminalCapabilities) {
		this.terminalCapabilities = terminalCapabilities;
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
	 * @return the terminalProfileName
	 */
	public String getTerminalProfileName() {
		return terminalProfileName;
	}

	/**
	 * @param terminalProfileName the terminalProfileName to set
	 */
	public void setTerminalProfileName(String terminalProfileName) {
		this.terminalProfileName = terminalProfileName;
	}

	/**
	 * @return the oldProfileId
	 */
	public Long getOldProfileId() {
		return oldProfileId;
	}

	/**
	 * @param oldProfileId the oldProfileId to set
	 */
	public void setOldProfileId(Long oldProfileId) {
		this.oldProfileId = oldProfileId;
	}

	

}
