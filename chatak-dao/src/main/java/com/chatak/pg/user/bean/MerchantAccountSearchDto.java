/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;

/**
 * @Author: Girmiti Software
 * @Date: Mar 2, 2016
 * @Time: 6:04:16 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class MerchantAccountSearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long accountId;

	private String businessName;
	
	private String parentBusinessName;
	
	private String merchantCode;
	
	private Long parentMerchantCode;
	
	private Long merchantAccountNumber;
	
	private String currentBalance;
	
	private String bankAccountName;
	
	private String accountStatus;
	
	private String bankState;
	
	private String merchantType;
	
	private String reason;
	
	private Integer pageIndex;

	private Integer pageSize;
	
	private Integer noOfRecords;
	
	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * @return the merchantAccountNumber
	 */
	public Long getMerchantAccountNumber() {
		return merchantAccountNumber;
	}

	/**
	 * @param merchantAccountNumber the merchantAccountNumber to set
	 */
	public void setMerchantAccountNumber(Long merchantAccountNumber) {
		this.merchantAccountNumber = merchantAccountNumber;
	}

	/**
	 * @return the currentBalance
	 */
	public String getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the bankAccountName
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/**
	 * @param bankAccountName the bankAccountName to set
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return the bankState
	 */
	public String getBankState() {
		return bankState;
	}

	/**
	 * @param bankState the bankState to set
	 */
	public void setBankState(String bankState) {
		this.bankState = bankState;
	}

	/**
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}
	
	/**
	 * @param merchantType the merchantType to set
	 */
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	/**
	 * @return the merchantType
	 */
	public String getMerchantType() {
		return merchantType;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @param parentMerchantCode the parentMerchantCode to set
	 */
	public void setParentMerchantCode(Long parentMerchantCode) {
		this.parentMerchantCode = parentMerchantCode;
	}

	/**
	 * @return the noOfRecords
	 */
	public Integer getNoOfRecords() {
		return noOfRecords;
	}
	
	/**
	 * @return the parentMerchantCode
	 */
	public Long getParentMerchantCode() {
		return parentMerchantCode;
	}

	/**
	 * @param noOfRecords the noOfRecords to set
	 */
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	/**
	 * @return the parentBusinessName
	 */
	public String getParentBusinessName() {
		return parentBusinessName;
	}

	/**
	 * @param parentBusinessName the parentBusinessName to set
	 */
	public void setParentBusinessName(String parentBusinessName) {
		this.parentBusinessName = parentBusinessName;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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