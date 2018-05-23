/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.bean.Response;

/**
 * @Author: Girmiti Software
 * @Date: May 3, 2018
 * @Time: 12:43:24 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class CardProgramRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long cardProgramId;
	
	private String cardProgramName;

	private String cardProgramDesc;

	private Long partnerId;

	private String partnerName;
	
	private Long bankId;

	private Long bin;

	private String binExt;

	private String cardDesign;

	private Long pinLength;

	private String pinGenAlgorithm;

	private String pinVerifiAlgorithm;

/*	private Long cardInactivityPeriod;*/

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

	/*private String cardInactivityPeriodDuration;*/

	

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

	public Long getBin() {
		return bin;
	}

	public void setBin(Long bin) {
		this.bin = bin;
	}

	public String getBinExt() {
		return binExt;
	}

	public void setBinExt(String binExt) {
		this.binExt = binExt;
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

	/*public Long getCardInactivityPeriod() {
		return cardInactivityPeriod;
	}

	public void setCardInactivityPeriod(Long cardInactivityPeriod) {
		this.cardInactivityPeriod = cardInactivityPeriod;
	}*/

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

	/*public String getCardInactivityPeriodDuration() {
		return cardInactivityPeriodDuration;
	}

	public void setCardInactivityPeriodDuration(String cardInactivityPeriodDuration) {
		this.cardInactivityPeriodDuration = cardInactivityPeriodDuration;
	}*/

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
}
