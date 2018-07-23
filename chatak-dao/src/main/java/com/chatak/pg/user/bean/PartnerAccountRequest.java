package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class PartnerAccountRequest implements Serializable {

	private static final long serialVersionUID = -2359859830211729881L;

	private Long partnerAccountId;

	private Long partnerId;

	private Long accountNumber;

	private String accountType;

	private Double availableBalance;

	private Double currentBalance;

	private String status;

	private Timestamp createdDate;

	private Timestamp updatedDate;

	private String createdBy;

	private String updatedBy;
	
	private String nickName;
	
	private Long programManagerId;
	
	private Double accountThresholdAmount; 
	
	private Double differenceAmount;
	
	private Boolean autoRepenish;
	
	private String sendFundsMode;
	
	private Long bankId;
	
	private Map<Long,String> banks;

	public Long getPartnerAccountId() {
		return partnerAccountId;
	}

	public void setPartnerAccountId(Long partnerAccountId) {
		this.partnerAccountId = partnerAccountId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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
	 * @return the differenceAmount
	 */
	public Double getDifferenceAmount() {
		return differenceAmount;
	}

	/**
	 * @param differenceAmount the differenceAmount to set
	 */
	public void setDifferenceAmount(Double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	/**
	 * @return the autoRepenish
	 */
	public Boolean getAutoRepenish() {
		return autoRepenish;
	}

	/**
	 * @param autoRepenish the autoRepenish to set
	 */
	public void setAutoRepenish(Boolean autoRepenish) {
		this.autoRepenish = autoRepenish;
	}

	/**
	 * @return the accountThresholdAmount
	 */
	public Double getAccountThresholdAmount() {
		return accountThresholdAmount;
	}

	/**
	 * @param accountThresholdAmount the accountThresholdAmount to set
	 */
	public void setAccountThresholdAmount(Double accountThresholdAmount) {
		this.accountThresholdAmount = accountThresholdAmount;
	}

	/**
	 * @return the sendFundsMode
	 */
	public String getSendFundsMode() {
		return sendFundsMode;
	}

	/**
	 * @param sendFundsMode the sendFundsMode to set
	 */
	public void setSendFundsMode(String sendFundsMode) {
		this.sendFundsMode = sendFundsMode;
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

	/**
	 * @return the banks
	 */
	public Map<Long, String> getBanks() {
		return banks;
	}

	/**
	 * @param banks the banks to set
	 */
	public void setBanks(Map<Long, String> banks) {
		this.banks = banks;
	}
}
