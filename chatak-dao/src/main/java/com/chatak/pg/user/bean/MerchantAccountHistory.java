/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: Girmiti Software
 * @Date: Jun 17, 2015
 * @Time: 11:27:25 AM
 * @Version: 1.0
 * @Comments:
 */
public class MerchantAccountHistory implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8748588004440338179L;

  private Long id;

  private String entityId;

  private String entityType;

  private String accountDesc;

  private Long accountNum;

  private String category;

  private Long currentBalance;

  private Long availableBalance;

  private String currency;

  private Integer autoPaymentLimit;

  private String autoPaymentMethod;

  private Integer autoTransferDay;

  private String status;

  private Timestamp updatedDate;

  private Long feeBalance;
  
 private String paymentMethod;
  
 private String transactionId;
 
 private Integer pageIndex;

 private Integer pageSize;
   
 private Integer noOfRecords;
 
 private String updatedDateString;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the entityId
   */
  public String getEntityId() {
    return entityId;
  }

  /**
   * @param entityId the entityId to set
   */
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  /**
   * @return the entityType
   */
  public String getEntityType() {
    return entityType;
  }

  /**
   * @param entityType the entityType to set
   */
  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  /**
   * @return the accountDesc
   */
  public String getAccountDesc() {
    return accountDesc;
  }

  /**
   * @param accountDesc the accountDesc to set
   */
  public void setAccountDesc(String accountDesc) {
    this.accountDesc = accountDesc;
  }

  /**
   * @return the accountNum
   */
  public Long getAccountNum() {
    return accountNum;
  }

  /**
   * @param accountNum the accountNum to set
   */
  public void setAccountNum(Long accountNum) {
    this.accountNum = accountNum;
  }

  /**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @return the currentBalance
   */
  public Long getCurrentBalance() {
    return currentBalance;
  }

  /**
   * @param currentBalance the currentBalance to set
   */
  public void setCurrentBalance(Long currentBalance) {
    this.currentBalance = currentBalance;
  }

  /**
   * @return the availableBalance
   */
  public Long getAvailableBalance() {
    return availableBalance;
  }

  /**
   * @param availableBalance the availableBalance to set
   */
  public void setAvailableBalance(Long availableBalance) {
    this.availableBalance = availableBalance;
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

  /**
   * @return the autoPaymentLimit
   */
  public Integer getAutoPaymentLimit() {
    return autoPaymentLimit;
  }

  /**
   * @param autoPaymentLimit the autoPaymentLimit to set
   */
  public void setAutoPaymentLimit(Integer autoPaymentLimit) {
    this.autoPaymentLimit = autoPaymentLimit;
  }

  /**
   * @return the autoPaymentMethod
   */
  public String getAutoPaymentMethod() {
    return autoPaymentMethod;
  }

  /**
   * @param autoPaymentMethod the autoPaymentMethod to set
   */
  public void setAutoPaymentMethod(String autoPaymentMethod) {
    this.autoPaymentMethod = autoPaymentMethod;
  }

  /**
   * @return the autoTransferDay
   */
  public Integer getAutoTransferDay() {
    return autoTransferDay;
  }

  /**
   * @param autoTransferDay the autoTransferDay to set
   */
  public void setAutoTransferDay(Integer autoTransferDay) {
    this.autoTransferDay = autoTransferDay;
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
   * @param updatedDate the updatedDate to set
   */
  public void setUpdatedDate(Timestamp updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the feeBalance
   */
  public Long getFeeBalance() {
    return feeBalance;
  }

  /**
   * @param feeBalance the feeBalance to set
   */
  public void setFeeBalance(Long feeBalance) {
    this.feeBalance = feeBalance;
  }

  /**
   * @return the paymentMethod
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * @return the transactionId
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * @param transactionId the transactionId to set
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * @return the pageIndex
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * @return the updatedDateString
   */
  public String getUpdatedDateString() {
  	return updatedDateString;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @param noOfRecords the noOfRecords to set
   */
  public void setNoOfRecords(Integer noOfRecords) {
    this.noOfRecords = noOfRecords;
  }
  
  /**
   * @param updatedDateString the updatedDateString to set
   */
  public void setUpdatedDateString(String updatedDateString) {
  	this.updatedDateString = updatedDateString;
  }

  /**
   * @return the noOfRecords
   */
  public Integer getNoOfRecords() {
    return noOfRecords;
  }

  /**
  * @param pageIndex the pageIndex to set
  */
  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
  * @return the pageSize
  */
  public Integer getPageSize() {
    return pageSize;
  }

}
