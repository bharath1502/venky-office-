/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: May 3, 2018
 * @Time: 12:43:24 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class CardProgramRequest extends Response implements Serializable {
	
	private static final long serialVersionUID = -2808023815822615671L;

	private Long cardProgramId;
	
	private String cardProgramName;

	private String cardProgramDesc;

	private Long partnerId;

	private String partnerName;
	
	private Long bankId;

	private String cardDesign;

	private Long pinLength;

	private String pinGenAlgorithm;

	private String pinVerifiAlgorithm;

	private Long pinRetryAttempts;

	private String cardExpriryPeriod;

	private String acceptance;

	private String serviceCode;

	private String message;

	private String defaultFirstName;

	private String defaultLastName;

	private boolean photoCard;

	private String status;

	private String reason;
	
	private String cardType;
	
	private List<String> statusList;
	
	private List<Long> partnerIdIn;
	
	private Long accountProgramId;
	
	private String applicationId;
	
	private List<String> cardTypeList;

	private Long agentId;
	
    private byte[] frontImageLogo;
	
	private byte[] backImageLogo;
	
	private String accountProgramName;
	
	private Long customerGroup;
	
	private List<Long> cardProgramIds;
	
	private String createdDate;
	
	private String updatedDate;
	
	private Long issuanceCardProgramId;
	
	private String currencyAlpaNumeric;
	
	private String issuanceCardProgramName;
	
	private String createdBy;
	
	private Long iin;
	
	private String iinExt;
	
	private String programManagerName;
	
	private String currency;
	
	private Long partnerIINCode;	
		
	private String isoName;
	
	private Long programManagerId;
	
	private String partnerCode;
	
	public Long getPartnerIINCode() {
		return partnerIINCode;
	}

	public void setPartnerIINCode(Long partnerIINCode) {
		this.partnerIINCode = partnerIINCode;
	}

	private Long isoId;
	
	private String entityName;
	
	/**
	 * @return the programManagerId
	 */
	public Long getProgramManagerId() {
		return programManagerId;
	}

	/**
	 * @param programManagerId the programManagerId to set
	 */
	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	/**
	 * @return the accountProgramId
	 */
	public Long getAccountProgramId() {
		return accountProgramId;
	}

	/**
	 * @param accountProgramId the accountProgramId to set
	 */
	public void setAccountProgramId(Long accountProgramId) {
		this.accountProgramId = accountProgramId;
	}

	public List<Long> getPartnerIdIn() {
		return partnerIdIn;
	}

	public void setPartnerIdIn(List<Long> partnerIdIn) {
		this.partnerIdIn = partnerIdIn;
	}

	public Long getCardProgramId() {
		return cardProgramId;
	}

	public void setCardProgramId(Long cardProgramId) {
		this.cardProgramId = cardProgramId;
	}

	public String getCardProgramName() {
		return cardProgramName;
	}

	public void setCardProgramName(String cardProgramName) {
		this.cardProgramName = cardProgramName;
	}

	public String getCardProgramDesc() {
		return cardProgramDesc;
	}

	public void setCardProgramDesc(String cardProgramDesc) {
		this.cardProgramDesc = cardProgramDesc;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	/**
	 * @return the bankId
	 */
	public Long getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getCardDesign() {
		return cardDesign;
	}

	public void setCardDesign(String cardDesign) {
		this.cardDesign = cardDesign;
	}

	public Long getPinLength() {
		return pinLength;
	}

	public void setPinLength(Long pinLength) {
		this.pinLength = pinLength;
	}

	public String getPinGenAlgorithm() {
		return pinGenAlgorithm;
	}

	public void setPinGenAlgorithm(String pinGenAlgorithm) {
		this.pinGenAlgorithm = pinGenAlgorithm;
	}

	public String getPinVerifiAlgorithm() {
		return pinVerifiAlgorithm;
	}

	public void setPinVerifiAlgorithm(String pinVerifiAlgorithm) {
		this.pinVerifiAlgorithm = pinVerifiAlgorithm;
	}

	public Long getPinRetryAttempts() {
		return pinRetryAttempts;
	}

	public void setPinRetryAttempts(Long pinRetryAttempts) {
		this.pinRetryAttempts = pinRetryAttempts;
	}

	public String getCardExpriryPeriod() {
		return cardExpriryPeriod;
	}

	public void setCardExpriryPeriod(String cardExpriryPeriod) {
		this.cardExpriryPeriod = cardExpriryPeriod;
	}

	public String getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDefaultFirstName() {
		return defaultFirstName;
	}

	public void setDefaultFirstName(String defaultFirstName) {
		this.defaultFirstName = defaultFirstName;
	}

	public String getDefaultLastName() {
		return defaultLastName;
	}

	public void setDefaultLastName(String defaultLastName) {
		this.defaultLastName = defaultLastName;
	}

	public boolean isPhotoCard() {
		return photoCard;
	}

	public void setPhotoCard(boolean photoCard) {
		this.photoCard = photoCard;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public List<String> getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(List<String> cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public byte[] getFrontImageLogo() {
		return frontImageLogo;
	}

	public void setFrontImageLogo(byte[] frontImageLogo) {
		this.frontImageLogo = frontImageLogo;
	}

	public byte[] getBackImageLogo() {
		return backImageLogo;
	}

	public void setBackImageLogo(byte[] backImageLogo) {
		this.backImageLogo = backImageLogo;
	}

	public String getAccountProgramName() {
		return accountProgramName;
	}

	public void setAccountProgramName(String accountProgramName) {
		this.accountProgramName = accountProgramName;
	}

	public Long getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(Long customerGroup) {
		this.customerGroup = customerGroup;
	}

	public List<Long> getCardProgramIds() {
		return cardProgramIds;
	}

	public void setCardProgramIds(List<Long> cardProgramIds) {
		this.cardProgramIds = cardProgramIds;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the issuanceCardProgramId
	 */
	public Long getIssuanceCardProgramId() {
		return issuanceCardProgramId;
	}

	/**
	 * @param issuanceCardProgramId the issuanceCardProgramId to set
	 */
	public void setIssuanceCardProgramId(Long issuanceCardProgramId) {
		this.issuanceCardProgramId = issuanceCardProgramId;
	}

	/**
	 * @return the currencyAlpaNumeric
	 */
	public String getCurrencyAlpaNumeric() {
		return currencyAlpaNumeric;
	}

	/**
	 * @param currencyAlpaNumeric the currencyAlpaNumeric to set
	 */
	public void setCurrencyAlpaNumeric(String currencyAlpaNumeric) {
		this.currencyAlpaNumeric = currencyAlpaNumeric;
	}

	/**
	 * @return the issuanceCardProgramName
	 */
	public String getIssuanceCardProgramName() {
		return issuanceCardProgramName;
	}

	/**
	 * @param issuanceCardProgramName the issuanceCardProgramName to set
	 */
	public void setIssuanceCardProgramName(String issuanceCardProgramName) {
		this.issuanceCardProgramName = issuanceCardProgramName;
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
	 * @return the iin
	 */
	public Long getIin() {
		return iin;
	}

	/**
	 * @param iin the iin to set
	 */
	public void setIin(Long iin) {
		this.iin = iin;
	}

	/**
	 * @return the iinExt
	 */
	public String getIinExt() {
		return iinExt;
	}

	/**
	 * @param iinExt the iinExt to set
	 */
	public void setIinExt(String iinExt) {
		this.iinExt = iinExt;
	}

	/**
	 * @return the programManagerName
	 */
	public String getProgramManagerName() {
		return programManagerName;
	}

	/**
	 * @param programManagerName the programManagerName to set
	 */
	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}
	/**
	 * @return
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Long getIsoId() {
		return isoId;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}

